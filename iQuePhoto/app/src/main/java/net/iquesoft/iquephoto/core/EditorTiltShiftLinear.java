package net.iquesoft.iquephoto.core;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.MotionEvent;

import net.iquesoft.iquephoto.core.editor.ImageEditorView;

// TODO: Linear tilt shift.
public class EditorTiltShiftLinear implements EditorTiltShift {
    private Paint mPaint;
    private Paint mShaderPaint;
    private Paint mTiltShiftLinearPaint;
    private Paint mTiltShiftLinearControlPaint;

    private RectF mBitmapRect;
    private RectF mTiltShiftLinearRect;
    private RectF mTempTiltShiftLinearRect;
    private RectF mTiltShiftLinearControlRect;

    private Matrix mGradientMatrix;

    private LinearGradient mLinearGradient;

    private ImageEditorView mImageEditorView;

    public EditorTiltShiftLinear(ImageEditorView imageEditorView) {
        mImageEditorView = imageEditorView;
    }

    @Override
    public void initialize() {
        mPaint = new Paint();

        mTiltShiftLinearPaint = new Paint();
        mTiltShiftLinearPaint.setAntiAlias(true);
        mTiltShiftLinearPaint.setFilterBitmap(false);
        mTiltShiftLinearPaint.setDither(true);

        mGradientMatrix = new Matrix();
        mTiltShiftLinearRect = new RectF();

        mShaderPaint = new Paint();
        mShaderPaint.setAntiAlias(true);
        mShaderPaint.setFilterBitmap(false);
        mShaderPaint.setDither(true);
        mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mTiltShiftLinearRect.isEmpty()) {
            canvas.saveLayer(mBitmapRect, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            mTiltShiftLinearControlRect.set(mTiltShiftLinearRect);
            //mTiltShiftLinearControlRect.inset(-mGradientInset, -mGradientInset);

            canvas.drawRect(mBitmapRect, mTiltShiftLinearPaint);
            canvas.drawOval(mTiltShiftLinearControlRect, mShaderPaint);
            canvas.restore();

            mTiltShiftLinearControlRect.set(mTiltShiftLinearRect);

            canvas.drawOval(mTiltShiftLinearRect, mTiltShiftLinearControlPaint);
        }
    }

    @Override
    public void updateRect(RectF bitmapRect) {

    }

    @Override
    public void updateGradientRect() {

    }

    @Override
    public void updateGradientMatrix(RectF rectF) {

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
