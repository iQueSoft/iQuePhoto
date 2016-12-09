package net.iquesoft.iquephoto.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class BitmapUtil {

    public static Uri getUriOfBitmap(Context context, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        String path = MediaStore.Images.Media.
                insertImage(context.getContentResolver(), bitmap, "iQuePhoto", null);

        return Uri.parse(path);
    }

    /*private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeScaledBitmapFromResource(Resources res, int resId,
                                                        int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }*/

    public static void logBitmapInfo(String bitmapName, Bitmap bitmap) {
        Log.i("Bitmap: " + bitmapName, "Height = " + String.valueOf(bitmap.getHeight()) +
                "\nWidth = " + String.valueOf(bitmap.getWidth()) + ".");
    }

    public static float dp2px(Context context, float dp) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.density * dp;
    }

    public static Bitmap getBlurImage(Context context, Bitmap bitmap, int width, int height) {
        Bitmap src = bitmap.copy(bitmap.getConfig(), true);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(src, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(scaledBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, scaledBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(3.75f);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    public static Bitmap drawable2Bitmap(Context context, int drawableId) {
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), drawableId, null);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return vectorDrawable2Bitmap((VectorDrawable) drawable);
            }
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap vectorDrawable2Bitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }
}
