package net.iquesoft.iquephoto.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import net.iquesoft.iquephoto.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
}
