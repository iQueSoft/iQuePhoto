package net.iquesoft.iquephoto.core;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

class Drawing {
    static final float TOUCH_TOLERANCE = 4f;
    static final float DEFAULT_STROKE_WIDTH = 10f;
    static final int DEFAULT_COLOR = Color.BLACK;

    private Paint mPaint;
    private Path mPath;

    Drawing(Paint paint, Path path) {
        mPaint = paint;
        mPath = path;
    }

    Paint getPaint() {
        return mPaint;
    }

    Path getPath() {
        return mPath;
    }
}
