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
    private Path mOriginalPath;

    Drawing(Paint paint, Path path, Path originalPath) {
        mPaint = paint;
        mPath = path;
        mOriginalPath = originalPath;
    }

    Paint getPaint() {
        return mPaint;
    }

    Path getPath() {
        return mPath;
    }

    Path getOriginalPath() {
        return mOriginalPath;
    }
}
