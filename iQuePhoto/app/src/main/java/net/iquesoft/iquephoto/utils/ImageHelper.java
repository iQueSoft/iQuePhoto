package net.iquesoft.iquephoto.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

public class ImageHelper {

    public static Bitmap getBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 8;

        return BitmapFactory.decodeFile(path, options);
    }

    public static String getPath(String... pathInSd) {

        StringBuilder path = new StringBuilder();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                path.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
            } else {
                path.append(Environment.getExternalStorageDirectory());
                path.append("/Picture");
            }
            File dir = new File(path.toString());
            if (!dir.exists()) {
                dir.mkdir();
            }
        } else {
            path.append(Environment.getDataDirectory().getAbsolutePath());
            File dir = new File(path.toString());
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        if (pathInSd != null) {
            for (String dir : pathInSd) {
                path.append(File.separator);
                path.append(dir);
                File dir2 = new File(path.toString());
                if (!dir2.exists()) {
                    dir2.mkdir();
                }
            }
        }
        return path.toString();
    }
}
