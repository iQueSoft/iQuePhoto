package net.iquesoft.iquephoto.core.editor.model;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
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
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.enums.EditorMode;
import net.iquesoft.iquephoto.util.BitmapUtil;
import net.iquesoft.iquephoto.util.MotionEventUtil;
import net.iquesoft.iquephoto.util.RectUtil;

import static net.iquesoft.iquephoto.core.editor.enums.EditorMode.*;

// TODO: FadeIn and FadeOut Animators.
public class EditorTiltShiftRadial implements EditorTiltShift {
    private static final int FADEOUT_DELAY = 1000;

    private float mFeather = 0.7f;
    private float mFocusRadius;
    private float mGradientInset = 100;
    private float mControlPointTolerance = 20;

    private float mPreX;
    private float mPreY;

    private float mPreDistance;

    private Context mContext;

    private Bitmap mBitmap;
    private Bitmap mBlurBitmap;

    private Paint mPaint;
    private Paint mShaderPaint;
    private Paint mTiltShiftRadialPaint;
    private Paint mTiltShiftRadialControlPaint;

    private RectF mBitmapRect = new RectF();
    private RectF mTiltShiftRadialRect;
    private RectF mTempTiltShiftRadialRect;
    private RectF mTiltShiftRadialControlRect;

    private Matrix mGradientMatrix;

    private RadialGradient mRadialGradient;

    private Animator mFadeInAnimator;
    private Animator mFadeOutAnimator;

    private EditorMode mMode = NONE;

    private ImageEditorView mImageEditorView;

    public EditorTiltShiftRadial(ImageEditorView imageEditorView) {
        mImageEditorView = imageEditorView;

        mContext = mImageEditorView.getContext();

        initialize(mContext);
    }

    @Override
    public void initialize(Context context) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        mPaint = new Paint();

        mTiltShiftRadialControlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTiltShiftRadialControlPaint.setColor(Color.WHITE);
        mTiltShiftRadialControlPaint.setStrokeWidth(metrics.density * 3.5f);
        mTiltShiftRadialControlPaint.setStyle(Paint.Style.STROKE);
        mTiltShiftRadialControlPaint.setAlpha(125);
        mTiltShiftRadialControlPaint.setDither(true);

        mTiltShiftRadialPaint = new Paint();
        mTiltShiftRadialPaint.setAntiAlias(true);
        mTiltShiftRadialPaint.setFilterBitmap(false);
        mTiltShiftRadialPaint.setDither(true);

        mGradientMatrix = new Matrix();

        mTempTiltShiftRadialRect = new RectF();
        mTiltShiftRadialRect = new RectF();
        mTiltShiftRadialControlRect = new RectF();

        mShaderPaint = new Paint();
        mShaderPaint.setAntiAlias(true);
        mShaderPaint.setFilterBitmap(false);
        mShaderPaint.setDither(true);
        mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        updateGradientRect();

        mControlPointTolerance *= 1.5f;

        mGradientInset = 0;

        mFadeInAnimator = ObjectAnimator.ofFloat(mImageEditorView, "paintAlpha", 0, 125);
        mFadeOutAnimator = ObjectAnimator.ofFloat(mImageEditorView, "paintAlpha", 125, 0);
        mFadeOutAnimator.setStartDelay(FADEOUT_DELAY);
    }

    @Override
    public void draw(Canvas canvas, Bitmap bitmap, Matrix matrix, Paint paint) {
        if (!mTiltShiftRadialRect.isEmpty()) {

            canvas.drawBitmap(bitmap, matrix, paint);

            canvas.saveLayer(mBitmapRect, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            mTiltShiftRadialControlRect.set(mTiltShiftRadialRect);
            mTiltShiftRadialControlRect.inset(-mGradientInset, -mGradientInset);
            
            if (mBlurBitmap == null) {
                mBitmap = bitmap;
                mBlurBitmap = BitmapUtil.getBlurImage(
                        mContext,
                        bitmap,
                        bitmap.getWidth(),
                        bitmap.getHeight());
            } else {
                if (!mBitmap.sameAs(bitmap)) {
                    mBitmap = bitmap;
                    mBlurBitmap = BitmapUtil.getBlurImage(
                            mContext,
                            bitmap,
                            bitmap.getWidth(),
                            bitmap.getHeight());
                }
            }

            if (mBlurBitmap != null) {
                canvas.drawBitmap(mBlurBitmap, matrix, paint);
            }

            canvas.drawCircle(
                    mTiltShiftRadialControlRect.centerX(),
                    mTiltShiftRadialControlRect.centerY(),
                    mFocusRadius,
                    mShaderPaint
            );

            canvas.restore();

            mTiltShiftRadialControlRect.set(mTiltShiftRadialRect);

            canvas.drawCircle(
                    mTiltShiftRadialRect.centerX(),
                    mTiltShiftRadialRect.centerY(),
                    mFocusRadius,
                    mTiltShiftRadialControlPaint
            );
        }
    }

    @Override
    public void updateRect(RectF bitmapRect) {
        if (bitmapRect.height() <= bitmapRect.width()) {
            mFocusRadius = bitmapRect.height() / 3;
        } else {
            mFocusRadius = bitmapRect.width() / 3;
        }

        mTiltShiftRadialRect.set(0, 0, mFocusRadius, mFocusRadius);

        mTiltShiftRadialRect.offsetTo(
                bitmapRect.centerX() - mFocusRadius / 2,
                bitmapRect.centerY() - mFocusRadius / 2
        );

        mBitmapRect.set(bitmapRect);

        updateGradientMatrix(mTiltShiftRadialRect);

        setPaintAlpha(125);
        mFadeOutAnimator.start();
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
    public void updateGradientShader(float value, Paint paint) {

    }

    @Override
    public void updateGradientMatrix(RectF rectF) {
        mGradientMatrix.reset();
        mGradientMatrix.postTranslate(rectF.centerX(), rectF.centerY());
        mGradientMatrix.postScale(rectF.height() / 2, rectF.height() / 2, rectF.centerX(), rectF.centerY());
        mRadialGradient.setLocalMatrix(mGradientMatrix);
    }

    @Override
    public void actionDown(MotionEvent event) {
        mFadeOutAnimator.cancel();

        if (getPaintAlpha() != 125) {
            mFadeInAnimator.start();
        }

        if (event.getPointerCount() == 1) {
            mMode = MOVE;
        }

        mPreX = event.getX();
        mPreY = event.getY();
    }

    @Override
    public void actionMove(MotionEvent event) {
        mTempTiltShiftRadialRect.set(mTiltShiftRadialRect);

        switch (mMode) {
            case MOVE:
                float distanceX = event.getX() - mPreX;
                float distanceY = event.getY() - mPreY;

                mTempTiltShiftRadialRect.offset(distanceX, distanceY);
                break;
            case RESIZE:
                float dist = MotionEventUtil.getFingersDistance(event);
                float scale = ((dist - mPreDistance) / displayDistance());

                mPreDistance = dist;

                scale += 1;
                scale *= scale;

                RectUtil.scaleRect(mTempTiltShiftRadialRect, scale);

                mFocusRadius = mTempTiltShiftRadialRect.height();

                break;
        }

        if (mTempTiltShiftRadialRect.width() > mControlPointTolerance
                && mTempTiltShiftRadialRect.height() > mControlPointTolerance) {
            if (isTiltShiftInRect()) {
                mTiltShiftRadialRect.set(mTempTiltShiftRadialRect);

                mPreX = event.getX();
                mPreY = event.getY();
            }

            updateGradientMatrix(mTiltShiftRadialRect);

            mImageEditorView.invalidate();

            ViewCompat.postInvalidateOnAnimation(mImageEditorView);
        }
    }

    @Override
    public void actionPointerDown(MotionEvent event) {

        if (event.getPointerCount() == 2) {
            mPreDistance = MotionEventUtil.getFingersDistance(event);
            mMode = RESIZE;
        }
    }

    @Override
    public void actionUp() {
        mFadeOutAnimator.start();

        mMode = NONE;
    }

    @Override
    public void actionPointerUp() {
        mMode = NONE;
    }

    @Override
    public void setPaintAlpha(int value) {
        mTiltShiftRadialControlPaint.setAlpha(value);
        mImageEditorView.postInvalidate();
    }

    @Override
    public int getPaintAlpha() {
        return mTiltShiftRadialControlPaint.getAlpha();
    }

    private float displayDistance() {

        float height = mImageEditorView.getHeight();
        float width = mImageEditorView.getWidth();

        return (float) Math.sqrt(width * width + height * height);
    }

    private boolean isTiltShiftInRect() {
        return mBitmapRect.contains(
                mTempTiltShiftRadialRect.centerX(),
                mTempTiltShiftRadialRect.centerY()
        );
    }
}
