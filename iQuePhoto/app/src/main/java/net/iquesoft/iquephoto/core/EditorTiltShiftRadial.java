package net.iquesoft.iquephoto.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;

import net.iquesoft.iquephoto.core.editor.ImageEditorView;

// TODO: Radial tilt shift.
public class EditorTiltShiftRadial implements EditorTiltShift {
    private float mFeather = 0.7f;
    private float mGradientInset = 100;
    private float mControlPointTolerance = 20;

    private Paint mPaint;
    private Paint mShaderPaint;
    private Paint mTiltShiftRadialPaint;
    private Paint mTiltShiftRadialControlPaint;

    private RectF mBitmapRect;
    private RectF mTiltShiftRadialRect;
    private RectF mTempTiltShiftRadialRect;
    private RectF mTiltShiftRadialControlRect;

    private Matrix mGradientMatrix;

    private RadialGradient mRadialGradient;

    private ImageEditorView mImageEditorView;

    public EditorTiltShiftRadial(ImageEditorView imageEditorView) {
        mImageEditorView = imageEditorView;
    }

    @Override
    public void initialize() {
        mPaint = new Paint();

        mTiltShiftRadialControlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTiltShiftRadialControlPaint.setColor(Color.WHITE);
        //mTiltShiftRadialControlPaint.setStrokeWidth(dp2px(metrics.density, 3.5f));
        mTiltShiftRadialControlPaint.setStyle(Paint.Style.STROKE);
        mTiltShiftRadialControlPaint.setAlpha(125);
        mTiltShiftRadialControlPaint.setDither(true);

        mTiltShiftRadialPaint = new Paint();
        mTiltShiftRadialPaint.setAntiAlias(true);
        mTiltShiftRadialPaint.setFilterBitmap(false);
        mTiltShiftRadialPaint.setDither(true);

        mGradientMatrix = new Matrix();

        mBitmapRect = new RectF();
        mTempTiltShiftRadialRect = new RectF();
        mTiltShiftRadialRect = new RectF();
        mTiltShiftRadialControlRect = new RectF();

        mShaderPaint = new Paint();
        mShaderPaint.setAntiAlias(true);
        mShaderPaint.setFilterBitmap(false);
        mShaderPaint.setDither(true);
        mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mTiltShiftRadialRect.isEmpty()) {
            canvas.saveLayer(mBitmapRect, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            mTiltShiftRadialControlRect.set(mTiltShiftRadialRect);
            mTiltShiftRadialControlRect.inset(-mGradientInset, -mGradientInset);

            //canvas.drawRect(mBitmapRect, mTiltShiftRadialPaint);
            canvas.drawBitmap(mImageEditorView.getAlteredBitmap(), mImageEditorView.getMatrix(), mPaint);
            canvas.drawOval(mTiltShiftRadialControlRect, mShaderPaint);
            canvas.restore();

            mTiltShiftRadialControlRect.set(mTiltShiftRadialRect);

            canvas.drawOval(mTiltShiftRadialRect, mTiltShiftRadialControlPaint);
        }
    }

    @Override
    public void updateRect(RectF bitmapRect) {
        RectF rect = bitmapRect;
        final boolean rect_changed = !mBitmapRect.equals(rect);

        if (null != rect) {
            if (rect_changed) {
                if (!mBitmapRect.isEmpty()) {
                    float old_left = mBitmapRect.left;
                    float old_top = mBitmapRect.top;
                    float old_width = mBitmapRect.width();
                    float old_height = mBitmapRect.height();

                    mTiltShiftRadialRect.inset(-(rect.width() - old_width) / 2, -(rect.height() - old_height) / 2);
                    mTiltShiftRadialRect.offset(rect.left - old_left, rect.top - old_top);
                    mTiltShiftRadialRect.offset((rect.width() - old_width) / 2, (rect.height() - old_height) / 2);
                } else {
                    mTiltShiftRadialRect.set(rect);
                    mTiltShiftRadialRect.inset(mControlPointTolerance, mControlPointTolerance);
                }
            }
            mBitmapRect.set(rect);
        } else {
            mBitmapRect.setEmpty();
            mTiltShiftRadialRect.setEmpty();
        }

        updateGradientMatrix(mTiltShiftRadialRect);
    }

    @Override
    public void updateGradientRect() {
        final int[] colors = new int[]{0xff000000, 0xff000000, 0};
        final float[] anchors = new float[]{0, mFeather, 1};

        mRadialGradient = new android.graphics.RadialGradient(
                0, 0, 1, colors, anchors, Shader.TileMode.CLAMP
        );
        updateGradientMatrix(mTiltShiftRadialRect);
    }

    @Override
    public void updateGradientMatrix(RectF rectF) {
        mGradientMatrix.reset();
        mGradientMatrix.postTranslate(rectF.centerX(), rectF.centerY());
        mGradientMatrix.postScale(rectF.height() / 2, rectF.height() / 2, rectF.centerX(), rectF.centerY());
        mRadialGradient.setLocalMatrix(mGradientMatrix);
    }

    @Override
    public void actionMove(MotionEvent event) {

    }

    @Override
    public void actionDown(MotionEvent event) {

    }

    @Override
    public void actionPointerDown(MotionEvent event) {

    }
}
