package net.iquesoft.iquephoto.core;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public interface EditorTiltShift {

    void initialize();

    void draw(Canvas canvas);

    void updateRect(RectF bitmapRect);

    void updateGradientRect();

    void updateGradientMatrix(RectF rectF);

    void actionMove(MotionEvent event);

    void actionDown(MotionEvent event);

    void actionPointerDown(MotionEvent event);
}
