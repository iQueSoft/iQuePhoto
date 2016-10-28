package net.iquesoft.iquephoto.utils;

import android.graphics.RectF;

public class RectUtil {

    public static void scaleRect(RectF rect, float scale) {
        float w = rect.width();
        float h = rect.height();

        float newW = scale * w;
        float newH = scale * h;

        float dx = (newW - w) / 2;
        float dy = (newH - h) / 2;

        rect.left -= dx;
        rect.top -= dy;
        rect.right += dx;
        rect.bottom += dy;
    }

    public static void rotateRect(RectF rect, float center_x, float center_y,
                                  float rotateAngle) {
        float x = rect.centerX();
        float y = rect.centerY();
        float sinA = (float) Math.sin(Math.toRadians(rotateAngle));
        float cosA = (float) Math.cos(Math.toRadians(rotateAngle));
        float newX = center_x + (x - center_x) * cosA - (y - center_y) * sinA;
        float newY = center_y + (y - center_y) * cosA + (x - center_x) * sinA;

        float dx = newX - x;
        float dy = newY - y;

        rect.offset(dx, dy);
    }
}
