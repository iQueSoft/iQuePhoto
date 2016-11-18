package net.iquesoft.iquephoto.util;

import android.graphics.Bitmap;
import android.util.Log;

import net.iquesoft.iquephoto.core.EditorCommand;

public class LoggerUtil {

    public static void bitmapInfo(Object object, Bitmap bitmap) {
        Log.i(LoggerUtil.class.getSimpleName() + ".Bitmap",
                "Bitmap in " + object.getClass().getSimpleName() +
                        "\nHeight = " + String.valueOf(bitmap.getHeight()) +
                        "\nWidth = " + String.valueOf(bitmap.getWidth()) + ".");
    }

    public static void applyInfo(EditorCommand editorCommand) {
        Log.i(LoggerUtil.class.getSimpleName() + ".EditorCommand", "Apply " + editorCommand.name());
    }
}
