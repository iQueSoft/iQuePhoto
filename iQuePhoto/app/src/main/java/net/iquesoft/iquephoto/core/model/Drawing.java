package net.iquesoft.iquephoto.core.model;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;

public class Drawing {
    public static final float DEFAULT_STROKE_WIDTH = 5f;
    public static final int DEFAULT_COLOR = Color.BLACK;

    private Paint mPaint;
    private Path mPath;

    public Drawing(@NonNull Paint paint, @NonNull Path path) {
        mPaint = paint;
        mPath = path;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public Path getPath() {
        return mPath;
    }
}