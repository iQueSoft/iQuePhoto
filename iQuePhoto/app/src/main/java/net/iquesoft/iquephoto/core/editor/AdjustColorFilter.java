package net.iquesoft.iquephoto.core.editor;

import android.graphics.ColorMatrix;
import android.support.annotation.IntRange;

public class AdjustColorFilter {
    public static ColorMatrix getWarmthMatrix(int value) {
        float warmth = (value / 220) / 2;

        return new ColorMatrix(new float[]{
                1, 0, 0, warmth, 0,
                0, 1, 0, warmth / 2, 0,
                0, 0, 1, warmth / 4, 0,
                0, 0, 0, 1, 0});
    }
}
