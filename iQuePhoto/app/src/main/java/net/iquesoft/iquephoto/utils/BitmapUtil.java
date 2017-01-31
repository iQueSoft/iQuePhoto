package net.iquesoft.iquephoto.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;

import com.isseiaoki.simplecropview.util.Logger;
import com.isseiaoki.simplecropview.util.Utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class BitmapUtil {
    public static Uri getUriOfBitmap(Context context, Bitmap bitmap) {
        Uri uri = Uri.fromFile(new File(context.getCacheDir(), "altered"));

        OutputStream outputStream = null;
        try {
            outputStream = context.getContentResolver()
                    .openOutputStream(uri);
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                return uri;
            }
        } catch (IOException e) {
            Logger.e("An error occurred while saving the image: " + uri, e);
        } finally {
            Utils.closeQuietly(outputStream);
        }

        return null;
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
