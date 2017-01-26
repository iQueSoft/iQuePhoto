package net.iquesoft.iquephoto.utils;

import android.graphics.Matrix;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

public class MatrixUtil {
    private static String TAG = MatrixUtil.class.getSimpleName();

    public static void matrixInfo(@NonNull String logPrefix, @NonNull Matrix matrix) {
        float x = getMatrixValue(matrix, Matrix.MTRANS_X);
        float y = getMatrixValue(matrix, Matrix.MTRANS_Y);
        float rScale = getScale(matrix);
        float rAngle = getAngle(matrix);
        Log.d(TAG, logPrefix + ": matrix + : { x: " + x + ", y: " + y + ", scale: " + rScale + ", angle: " + rAngle + " }");
    }

    private static float getMatrixValue(@NonNull Matrix matrix, @IntRange(from = 0, to = 9) int valueIndex) {
        float[] matrixValues = new float[9];
        matrix.getValues(matrixValues);

        return matrixValues[valueIndex];
    }

    public static float getMatrixX(@NonNull Matrix matrix) {
        return getMatrixValue(matrix, Matrix.MTRANS_X);
    }

    public static float getMatrixY(@NonNull Matrix matrix) {
        return getMatrixValue(matrix, Matrix.MTRANS_Y);
    }

    public static float getScale(@NonNull Matrix matrix) {
        return (float) Math.sqrt(Math.pow(getMatrixValue(matrix, Matrix.MSCALE_X), 2)
                + Math.pow(getMatrixValue(matrix, Matrix.MSKEW_Y), 2));
    }

    public static float getAngle(@NonNull Matrix matrix) {
        return (float) -(Math.atan2(getMatrixValue(matrix, Matrix.MSKEW_X),
                getMatrixValue(matrix, Matrix.MSCALE_X)) * (180 / Math.PI));
    }
}
