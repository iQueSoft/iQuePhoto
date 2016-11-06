package net.iquesoft.iquephoto.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

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

    public static Bitmap decodeScaledBitmapFromResource(Resources res, int resId,
                                                        int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.outHeight = reqHeight;
        options.outWidth = reqWidth;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
