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
import net.iquesoft.iquephoto.util.BitmapUtil;
import net.iquesoft.iquephoto.util.MotionEventUtil;

// TODO: Radial tilt shift.
public class EditorTiltShiftRadial implements EditorTiltShift {
    private static final int FADEOUT_DELAY = 3000;

    private int mPointerCount = 0;

    private float mFeather = 0.7f;
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
    public void actionMove(MotionEvent event) {
        mTempTiltShiftRadialRect.set(mTiltShiftRadialRect);

        if (mPointerCount == 1) {
            float distanceX = event.getX() - mPreX;
            float distanceY = event.getY() - mPreY;

            mTempTiltShiftRadialRect.offset(distanceX, distanceY);
        } else {
            float dist = MotionEventUtil.distanceBetweenFingers(event);
            float scale = ((dist - mPreDistance) / displayDistance());

            mPreDistance = dist;

            scale += 1;
            scale *= scale;

            // TODO: Radial tilt shift resize.
            mTempTiltShiftRadialRect.inset(scale, scale);
        }

        if (mTempTiltShiftRadialRect.width() > mControlPointTolerance
                && mTempTiltShiftRadialRect.height() > mControlPointTolerance) {
            mTiltShiftRadialRect.set(mTempTiltShiftRadialRect);

            mPreX = event.getX();
            mPreY = event.getY();
        }

        updateGradientMatrix(mTiltShiftRadialRect);

        mImageEditorView.invalidate();

        ViewCompat.postInvalidateOnAnimation(mImageEditorView);
    }

    @Override
    public void actionDown(MotionEvent event) {
        mFadeOutAnimator.cancel();

        if (getPaintAlpha() != 125) {
            mFadeInAnimator.start();
        }

        mPointerCount = event.getPointerCount();

        mPreX = event.getX();
        mPreY = event.getY();
    }

    @Override
    public void actionPointerDown(MotionEvent event) {

        mPointerCount = event.getPointerCount();

        if (event.getPointerCount() == 2) {
            mPreDistance = MotionEventUtil.distanceBetweenFingers(event);
        }
    }

    @Override
    public void actionUp() {
        mFadeOutAnimator.start();

        mPointerCount = 0;
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
}
