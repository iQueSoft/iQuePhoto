package net.iquesoft.iquephoto.core.editor.model;

import android.content.Context;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.enums.EditorMode;
import net.iquesoft.iquephoto.util.MotionEventUtil;
import net.iquesoft.iquephoto.util.RectUtil;

import static net.iquesoft.iquephoto.core.editor.enums.EditorMode.*;

public class EditorVignette {

    private float mFeather = 0.7f;

    private static final int FADEOUT_DELAY = 3000;

    private float mPreX;
    private float mPreY;

    private float mPreDistance;

    private float mGradientInset = 100;
    private float mControlPointTolerance = 20;

    private final RectF mBitmapRect = new RectF();

    private Paint mShaderPaint;
    private Paint mVignettePaint;
    private Paint mVignetteControlPaint;

    private final Paint mPaint = new Paint();

    private RectF mVignetteRect;

    private final RectF mTempVignetteRect = new RectF();
    private final RectF mVignetteControlRect = new RectF();

    private RadialGradient mRadialGradient;

    private Matrix mGradientMatrix;

    private EditorMode mMode = NONE;

    private ImageEditorView mImageEditorView;

    public EditorVignette(ImageEditorView imageEditorView) {
        mImageEditorView = imageEditorView;

        initializeVignette(imageEditorView.getContext());
    }

    private void initializeVignette(Context context) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        mVignetteControlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVignetteControlPaint.setColor(Color.WHITE);
        mVignetteControlPaint.setStrokeWidth(dp2px(metrics.density, 3.5f));
        mVignetteControlPaint.setStyle(Paint.Style.STROKE);
        mVignetteControlPaint.setAlpha(125);
        mVignetteControlPaint.setDither(true);

        mVignettePaint = new Paint();
        mVignettePaint.setAntiAlias(true);
        mVignettePaint.setFilterBitmap(false);
        mVignettePaint.setDither(true);

        mGradientMatrix = new Matrix();
        mVignetteRect = new RectF();

        updateMask(55);

        mShaderPaint = new Paint();
        mShaderPaint.setAntiAlias(true);
        mShaderPaint.setFilterBitmap(false);
        mShaderPaint.setDither(true);
        mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        updateGradientShader(0.7f, mShaderPaint);

        mControlPointTolerance = mControlPointTolerance * 1.5f;

        mGradientInset = dp2px(metrics.density, 0);
    }

    public void updateMask(int value) {
        if (value >= 0) {
            mVignetteControlPaint.setColor(Color.WHITE);
            mVignettePaint.setColor(Color.BLACK);
        } else {
            mVignetteControlPaint.setColor(Color.BLACK);
            mVignettePaint.setColor(Color.WHITE);
        }

        value = Math.max(Math.min(Math.abs(value), 100), 0);
        value *= 2.55;

        mVignetteControlPaint.setAlpha(125);
        mVignettePaint.setAlpha(value);
    }

    private void updateGradientShader(float value, final Paint paint) {

        mFeather = value;
        final int[] colors = new int[]{0xff000000, 0xff000000, 0};
        final float[] anchors = new float[]{0, mFeather, 1};

        mRadialGradient = new android.graphics.RadialGradient(
                0, 0, 1, colors, anchors, Shader.TileMode.CLAMP
        );
        paint.setShader(mRadialGradient);
        updateGradientMatrix(mVignetteRect);
    }

    public void updateGradientMatrix(RectF rectF) {
        mGradientMatrix.reset();
        mGradientMatrix.postTranslate(rectF.centerX(), rectF.centerY());
        mGradientMatrix.postScale(rectF.width() / 2, rectF.height() / 2, rectF.centerX(), rectF.centerY());
        mRadialGradient.setLocalMatrix(mGradientMatrix);
    }

    public void updateRect(RectF bitmapRectF) {
        RectF rect = bitmapRectF;
        final boolean rect_changed = !mBitmapRect.equals(rect);

        if (null != rect) {
            if (rect_changed) {
                if (!mBitmapRect.isEmpty()) {
                    float old_left = mBitmapRect.left;
                    float old_top = mBitmapRect.top;
                    float old_width = mBitmapRect.width();
                    float old_height = mBitmapRect.height();

                    mVignetteRect.inset(-(rect.width() - old_width) / 2, -(rect.height() - old_height) / 2);
                    mVignetteRect.offset(rect.left - old_left, rect.top - old_top);
                    mVignetteRect.offset((rect.width() - old_width) / 2, (rect.height() - old_height) / 2);
                } else {
                    mVignetteRect.set(rect);
                    mVignetteRect.inset(mControlPointTolerance, mControlPointTolerance);
                }
            }
            mBitmapRect.set(rect);
        } else {
            mBitmapRect.setEmpty();
            mVignetteRect.setEmpty();
        }

        updateGradientMatrix(mVignetteRect);
    }
    
    public void draw(@NonNull Canvas canvas) {
        if (!mVignetteRect.isEmpty()) {
            canvas.saveLayer(mBitmapRect, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            mVignetteControlRect.set(mVignetteRect);
            mVignetteControlRect.inset(-mGradientInset, -mGradientInset);

            canvas.drawRect(mBitmapRect, mVignettePaint);
            canvas.drawOval(mVignetteControlRect, mShaderPaint);

            canvas.restore();

            canvas.drawOval(mVignetteControlRect, mVignetteControlPaint);
        }
    }

    public void drawOnImage(Canvas canvas) {
        if (!mVignetteRect.isEmpty()) {
            canvas.saveLayer(0, 0, canvas.getHeight(), canvas.getWidth(), mPaint, Canvas.ALL_SAVE_FLAG);

            mVignetteControlRect.set(mVignetteRect);
            mVignetteControlRect.inset(-mGradientInset, -mGradientInset);

            canvas.drawRect(mBitmapRect, mVignettePaint);
            canvas.drawOval(mVignetteControlRect, mShaderPaint);
            canvas.restore();

            mVignetteControlRect.set(mVignetteRect);

            canvas.drawOval(mVignetteRect, mVignetteControlPaint);
        }
    }

    public void actionDown(MotionEvent motionEvent) {
        mMode = MOVE;

        mPreX = motionEvent.getX();
        mPreY = motionEvent.getY();
    }

    public void actionPointerDown(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            float angle = MotionEventUtil.getFingersAngle(event);

            if (angle < 0) {
                angle += 180;
            }

            if (angle > 36 && angle < 72 || angle > 108 && angle < 144) {
                mPreDistance = MotionEventUtil.getFingersDistance(event);

                mMode = RESIZE;
            } else if (angle >= 72 && angle <= 108) {
                // TODO: Log.i("Action", "Height");
            } else {
                // TODO: Log.i("Action", "Width");
            }

            Log.i("Angle", String.valueOf(angle));
        }
    }

    public void actionMove(MotionEvent event) {
        mTempVignetteRect.set(mVignetteRect);

        switch (mMode) {
            case MOVE:
                float distanceX = event.getX() - mPreX;
                float distanceY = event.getY() - mPreY;

                mTempVignetteRect.offset(distanceX, distanceY);
                break;
            case RESIZE:
                float dist = MotionEventUtil.getFingersDistance(event);
                float displayDistance = MotionEventUtil.getDisplayDistance(
                        mImageEditorView.getWidth(),
                        mImageEditorView.getHeight()
                );

                float scale = ((dist - mPreDistance) / displayDistance);

                mPreDistance = dist;

                scale += 1;
                scale *= scale;

                RectUtil.scaleRect(mTempVignetteRect, scale);

                break;
        }

        if (mTempVignetteRect.width() > mControlPointTolerance
                && mTempVignetteRect.height() > mControlPointTolerance) {
            mVignetteRect.set(mTempVignetteRect);

            mPreX = event.getX();
            mPreY = event.getY();
        }

        updateGradientMatrix(mVignetteRect);

        mImageEditorView.invalidate();
    }

    public void actionUp() {
        mMode = NONE;
    }

    public void actionPointerUp() {
        mMode = MOVE;
    }

    private float getFingersAngle(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float dp2px(final float density, float dp) {
        return density * dp;
    }
}
