package net.iquesoft.iquephoto.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public class ColorCircleDrawable extends Drawable {
    private boolean mIsSelected;

    private final Paint mPaint;
    private final Paint mColorPaint;

    public ColorCircleDrawable(final int color) {
        mColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorPaint.setColor(color);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setAlpha(155);
    }

    @Override
    public void draw(@NonNull final Canvas canvas) {
        final int mRadius = 40;
        final Rect bounds = getBounds();

        if (mIsSelected) {
            canvas.drawCircle(bounds.centerX(), bounds.centerY(), mRadius + 15, mPaint);
        }

        canvas.drawCircle(bounds.centerX(), bounds.centerY(), mRadius, mColorPaint);
    }

    @Override
    public void setAlpha(final int alpha) {
        mColorPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(final ColorFilter cf) {
        mColorPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;

        invalidateSelf();
    }
}