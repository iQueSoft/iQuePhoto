package net.iquesoft.iquephoto.utils;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class AdjustUtil {

    public static Paint getExposurePaint(int value) {
        float exposure = 1 + value / 100;//(float) Math.pow(2, value / 100);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(new float[]
                {
                        exposure, 0, 0, 0, 0,
                        0, exposure, 0, 0, 0,
                        0, 0, exposure, 0, 0,
                        0, 0, 0, 1, 0
                }));

        return paint;
    }
}
