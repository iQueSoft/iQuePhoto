package net.iquesoft.iquephoto.util;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

public class LogHelper {
    private static final String TAG = LogHelper.class.getSimpleName();

    public static void logMatrix(@NonNull String prefix, @NonNull Matrix matrix) {
        float x = getMatrixValue(matrix, Matrix.MTRANS_X);
        float y = getMatrixValue(matrix, Matrix.MTRANS_Y);
        float rScale = getMatrixScale(matrix);
        float rAngle = getMatrixAngle(matrix);
        Log.d(TAG, "Matrix - " + prefix + " -->" + "\n" +
                "X = " + x + "\n" +
                "Y = " + y + "\n" +
                "Scale = " + rScale + "\n" +
                "Angle = " + rAngle
        );
    }

    public static void logRect(@NonNull String prefix, @NonNull Rect rect) {
        Log.i(TAG, "RectF - " + prefix + " --> " + "\n" +
                "left (X) = " + String.valueOf(rect.left) + "\n" +
                "top (Y) = " + String.valueOf(rect.top) + "\n" +
                "right (X1) = " + String.valueOf(rect.right) + "\n" +
                "bottom (Y1) = " + String.valueOf(rect.bottom)
        );
    }

    public static void logRect(@NonNull String prefix, @NonNull RectF rectF) {
        Log.i(TAG, "RectF - " + prefix + " --> " + "\n" +
                "left (X) = " + String.valueOf(rectF.left) + "\n" +
                "top (Y) = " + String.valueOf(rectF.top) + "\n" +
                "right (X1) = " + String.valueOf(rectF.right) + "\n" +
                "bottom (Y1) = " + String.valueOf(rectF.bottom)
        );
    }

    private static float getMatrixValue(@NonNull Matrix matrix, @IntRange(from = 0, to = 9) int valueIndex) {
        float[] matrixValues = new float[9];
        matrix.getValues(matrixValues);

        return matrixValues[valueIndex];
    }

    private static float getMatrixScale(@NonNull Matrix matrix) {
        return (float) Math.sqrt(Math.pow(getMatrixValue(matrix, Matrix.MSCALE_X), 2)
                + Math.pow(getMatrixValue(matrix, Matrix.MSKEW_Y), 2));
    }

    private static float getMatrixAngle(@NonNull Matrix matrix) {
        return (float) -(Math.atan2(getMatrixValue(matrix, Matrix.MSKEW_X),
                getMatrixValue(matrix, Matrix.MSCALE_X)) * (180 / Math.PI));
    }
}
