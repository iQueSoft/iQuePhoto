package net.iquesoft.iquephoto.core.editor.model;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import net.iquesoft.iquephoto.core.editor.enums.EditorMode;
import net.iquesoft.iquephoto.utils.MotionEventUtil;
import net.iquesoft.iquephoto.utils.RectUtil;

import static net.iquesoft.iquephoto.core.editor.enums.EditorMode.*;

// TODO: FadeIn and FadeOut Animators.
public class EditorRadialTiltShift {
    private static final int FADEOUT_DELAY = 1000;

    private float mFeather = 0.7f;
    private float mFocusRadius;
    private float mGradientInset = 100;
    private float mControlPointTolerance = 20;

    private float mPreX;
    private float mPreY;

    private int mViewWidth;
    private int mViewHeight;

    private float mPreDistance;

    private Bitmap mBitmap;
    private Bitmap mBlurBitmap;

    private Paint mPaint;
    private Paint mShaderPaint;
    private Paint mTiltShiftRadialPaint;
    private Paint mTiltShiftRadialControlPaint;

    private RectF mBitmapRect = new RectF();
    private RectF mRadialTiltShiftRect;
    private RectF mRadialTiltShiftTempRect;
    private RectF mRadialTiltShiftControlRect;

    private Matrix mGradientMatrix;

    private RadialGradient mRadialGradient;

    private Animator mFadeInAnimator;
    private Animator mFadeOutAnimator;

    private EditorMode mMode = NONE;

    public EditorRadialTiltShift(int viewWidth, int viewHeight) {
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;
        initialize();
    }

    public void initialize() {
        mPaint = new Paint();

        mTiltShiftRadialControlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTiltShiftRadialControlPaint.setColor(Color.WHITE);
        mTiltShiftRadialControlPaint.setStrokeWidth(5f);
        mTiltShiftRadialControlPaint.setStyle(Paint.Style.STROKE);
        mTiltShiftRadialControlPaint.setAlpha(125);
        mTiltShiftRadialControlPaint.setDither(true);

        mTiltShiftRadialPaint = new Paint();
        mTiltShiftRadialPaint.setAntiAlias(true);
        mTiltShiftRadialPaint.setFilterBitmap(false);
        mTiltShiftRadialPaint.setDither(true);

        mGradientMatrix = new Matrix();

        mRadialTiltShiftRect = new RectF();
        mRadialTiltShiftControlRect = new RectF();
        mRadialTiltShiftTempRect = new RectF();

        mShaderPaint = new Paint();
        mShaderPaint.setAntiAlias(true);
        mShaderPaint.setFilterBitmap(false);
        mShaderPaint.setDither(true);
        mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        updateGradientShader();

        mControlPointTolerance *= 1.5f;

        mGradientInset = 0;

        //mFadeInAnimator = ObjectAnimator.ofFloat(mImageEditorView, "paintAlpha", 0, 125);
        //mFadeOutAnimator = ObjectAnimator.ofFloat(mImageEditorView, "paintAlpha", 125, 0);
        //mFadeOutAnimator.setStartDelay(FADEOUT_DELAY);
    }

    public void draw(@NonNull Canvas canvas, @NonNull Bitmap bitmap, @NonNull Matrix matrix, @NonNull Paint paint) {
        if (!mRadialTiltShiftRect.isEmpty()) {

            canvas.drawBitmap(bitmap, matrix, paint);

            canvas.saveLayer(mBitmapRect, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            mRadialTiltShiftControlRect.set(mRadialTiltShiftRect);
            mRadialTiltShiftControlRect.inset(-mGradientInset, -mGradientInset);

            if (mBlurBitmap != null) {
                canvas.drawBitmap(mBlurBitmap, matrix, paint);
            }

            canvas.drawCircle(
                    mRadialTiltShiftControlRect.centerX(),
                    mRadialTiltShiftControlRect.centerY(),
                    mFocusRadius,
                    mShaderPaint
            );

            canvas.restore();

            mRadialTiltShiftControlRect.set(mRadialTiltShiftRect);

            canvas.drawCircle(
                    mRadialTiltShiftRect.centerX(),
                    mRadialTiltShiftRect.centerY(),
                    mFocusRadius,
                    mTiltShiftRadialControlPaint
            );
        }
    }

    public void updateRect(RectF bitmapRect) {
        if (bitmapRect.height() <= bitmapRect.width()) {
            mFocusRadius = bitmapRect.height() / 3;
        } else {
            mFocusRadius = bitmapRect.width() / 3;
        }

        mRadialTiltShiftRect.set(0, 0, mFocusRadius, mFocusRadius);

        mRadialTiltShiftRect.offsetTo(
                bitmapRect.centerX() - mFocusRadius / 2,
                bitmapRect.centerY() - mFocusRadius / 2
        );

        mBitmapRect.set(bitmapRect);

        updateGradientMatrix(mRadialTiltShiftRect);

        setPaintAlpha(125);
        //mFadeOutAnimator.start();
    }

    private void updateGradientShader() {
        final int[] colors = new int[]{0xff000000, 0xff000000, 0};
        final float[] anchors = new float[]{0, mFeather, 1};

        mRadialGradient = new android.graphics.RadialGradient(
                0, 0, 1, colors, anchors, Shader.TileMode.CLAMP
        );
        updateGradientMatrix(mRadialTiltShiftRect);
        mShaderPaint.setShader(mRadialGradient);
    }
    
    public void updateBlurBitmap(@NonNull Bitmap bitmap) {
        mBlurBitmap = bitmap;
    }

    private void updateGradientMatrix(RectF rectF) {
        mGradientMatrix.reset();
        mGradientMatrix.postTranslate(rectF.centerX(), rectF.centerY());
        mGradientMatrix.postScale(rectF.height() / 2, rectF.height() / 2, rectF.centerX(), rectF.centerY());
        mRadialGradient.setLocalMatrix(mGradientMatrix);
    }

    public void actionDown(MotionEvent event) {
        //mFadeOutAnimator.cancel();

        if (getPaintAlpha() != 125) {
            // mFadeInAnimator.start();
        }

        if (event.getPointerCount() == 1) {
            mMode = MOVE;
        }

        mPreX = event.getX();
        mPreY = event.getY();
    }

    public void actionMove(MotionEvent event) {
        mRadialTiltShiftTempRect.set(mRadialTiltShiftRect);

        switch (mMode) {
            case MOVE:
                float distanceX = event.getX() - mPreX;
                float distanceY = event.getY() - mPreY;

                mRadialTiltShiftTempRect.offset(distanceX, distanceY);
                break;
            case ROTATE_AND_SCALE:
                float dist = MotionEventUtil.getDelta(event);
                float scale = ((dist - mPreDistance) / displayDistance());

                mPreDistance = dist;

                scale += 1;
                scale *= scale;

                RectUtil.scaleRect(mRadialTiltShiftTempRect, scale);

                mFocusRadius = mRadialTiltShiftTempRect.height();

                break;
        }

        if (mRadialTiltShiftTempRect.width() > mControlPointTolerance
                && mRadialTiltShiftTempRect.height() > mControlPointTolerance) {
            if (isTiltShiftInRect()) {
                mRadialTiltShiftRect.set(mRadialTiltShiftTempRect);

                mPreX = event.getX();
                mPreY = event.getY();
            }

            updateGradientMatrix(mRadialTiltShiftRect);

        }
    }

    public void actionPointerDown(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            mPreDistance = MotionEventUtil.getDelta(event);
            mMode = ROTATE_AND_SCALE;
        }
    }

    public void actionUp() {
        // mFadeOutAnimator.start();

        mMode = NONE;
    }

    public void actionPointerUp() {
        mMode = NONE;
    }

    public void setPaintAlpha(int value) {
        mTiltShiftRadialControlPaint.setAlpha(value);
        //mImageEditorView.postInvalidate();
    }

    public int getPaintAlpha() {
        return mTiltShiftRadialControlPaint.getAlpha();
    }

    private float displayDistance() {
        return (float) Math.sqrt(mViewWidth * mViewWidth + mViewHeight * mViewHeight);
    }

    private boolean isTiltShiftInRect() {
        return mBitmapRect.contains(
                mRadialTiltShiftTempRect.centerX(),
                mRadialTiltShiftTempRect.centerY()
        );
    }
}