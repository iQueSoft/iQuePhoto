package net.iquesoft.iquephoto.core.editor.model;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;

import net.iquesoft.iquephoto.utils.SizeUtil;

public class Drawing {
    public static final float DEFAULT_STROKE_WIDTH = 5f;
    public static final int DEFAULT_COLOR = Color.BLACK;

    private Paint mPaint;
    private Path mPath;
    private Path mOriginalPath;

    public Drawing(@NonNull Paint paint, @NonNull Path path, Path originalPath) {
        mPaint = paint;
        mPath = path;
        mOriginalPath = originalPath;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public Path getPath() {
        return mPath;
    }

    // TODO: Make path for image (not for EditorImageView)
    public Path getOriginalPath() {
        return mOriginalPath;
    }
}
