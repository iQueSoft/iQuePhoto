package net.iquesoft.iquephoto.core;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class EditorTiltShift {

    public abstract void draw(Canvas canvas);

    public abstract void actionMove(MotionEvent event);

    public abstract void actionDown(MotionEvent event);

    public abstract void actionPointerDown(MotionEvent event);
}
