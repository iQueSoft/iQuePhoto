package net.iquesoft.iquephoto.core.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public interface EditorTiltShift {

    void initialize(Context context);

    void draw(Canvas canvas, Bitmap bitmap, Matrix matrix, Paint paint);

    void updateRect(RectF bitmapRect);

    void updateGradientRect();

    void updateGradientShader(float value, final Paint paint);

    void updateGradientMatrix(RectF rectF);

    void actionMove(MotionEvent event);

    void actionDown(MotionEvent event);

    void actionPointerDown(MotionEvent event);

    void actionUp();

    void actionPointerUp();

    void setPaintAlpha(int value);

    int getPaintAlpha();
}
