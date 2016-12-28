package net.iquesoft.iquephoto.util;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Log;

public class RectUtil {
    public static void logRectInfo(String prefix, @NonNull RectF rect) {
        Log.i("Rect info", prefix + "\n" + rect.toString());
    }

    public static void scaleRect(RectF rectF, float scale) {
        float w = rectF.width();
        float h = rectF.height();

        float newW = scale * w;
        float newH = scale * h;

        float dx = (newW - w) / 2;
        float dy = (newH - h) / 2;

        rectF.left -= dx;
        rectF.top -= dy;
        rectF.right += dx;
        rectF.bottom += dy;
    }

    public static void rotateRect(RectF rectF, float centerX, float centerY,
                                  float rotateAngle) {
        float x = rectF.centerX();
        float y = rectF.centerY();

        float sinA = (float) Math.sin(Math.toRadians(rotateAngle));
        float cosA = (float) Math.cos(Math.toRadians(rotateAngle));

        float newX = centerX + (x - centerX) * cosA - (y - centerY) * sinA;
        float newY = centerY + (y - centerY) * cosA + (x - centerX) * sinA;

        float dx = newX - x;
        float dy = newY - y;

        rectF.offset(dx, dy);
    }
}
