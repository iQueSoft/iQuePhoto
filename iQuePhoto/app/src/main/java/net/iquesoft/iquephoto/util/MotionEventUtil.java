package net.iquesoft.iquephoto.util;

import android.view.MotionEvent;

public class MotionEventUtil {

    public static float getDisplayDistance(int width, int height) {
        return (float) Math.sqrt(width * width + height * height);
    }

    public static float getFingersDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float) Math.sqrt(x * x + y * y);
    }

    public static float getFingersAngle(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}
