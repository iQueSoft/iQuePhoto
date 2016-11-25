package net.iquesoft.iquephoto.util;

import android.view.MotionEvent;

public class MotionEventUtil {
    
    public static float getFingersDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float) Math.sqrt(x * x + y * y);
    }
}
