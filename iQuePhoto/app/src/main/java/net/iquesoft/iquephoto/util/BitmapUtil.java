package net.iquesoft.iquephoto.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapUtil {

    public static Uri getBitmapUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String title = "iQuePhoto_" + timeStamp;
        String path = MediaStore.Images.Media.
                insertImage(context.getContentResolver(), bitmap, title, null);

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
}
