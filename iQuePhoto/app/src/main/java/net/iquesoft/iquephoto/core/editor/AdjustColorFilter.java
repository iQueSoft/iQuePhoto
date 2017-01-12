package net.iquesoft.iquephoto.core.editor;

import android.graphics.ColorMatrix;

public class AdjustColorFilter {
    public static ColorMatrix getBrightnessMatrix(int value) {
        float brightness = value / 2;

        return new ColorMatrix(new float[]{
                1, 0, 0, 0, brightness,
                0, 1, 0, 0, brightness,
                0, 0, 1, 0, brightness,
                0, 0, 0, 1, 0});
    }

    public static ColorMatrix getContrastMatrix(int value) {
        float input = value / 100;
        float scale = input + 1f;
        float contrast = (-0.5f * scale + 0.5f) * 255f;

        return new ColorMatrix(new float[]{
                scale, 0, 0, 0, contrast,
                0, scale, 0, 0, contrast,
                0, 0, scale, 0, contrast,
                0, 0, 0, 1, 0});
    }

    public static ColorMatrix getSaturationMatrix(int value) {
        ColorMatrix colorMatrix = new ColorMatrix();

        float saturation = (value + 100) / 100f;

        colorMatrix.setSaturation(saturation);

        return colorMatrix;
    }

    public static ColorMatrix getWarmthMatrix(int value) {
        float warmth = (value / 220) / 2;

        return new ColorMatrix(new float[]{
                1, 0, 0, warmth, 0,
                0, 1, 0, warmth / 2, 0,
                0, 0, 1, warmth / 4, 0,
                0, 0, 0, 1, 0});
    }
}
