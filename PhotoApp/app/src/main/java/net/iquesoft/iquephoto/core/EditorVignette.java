package net.iquesoft.iquephoto.core;

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

import net.iquesoft.iquephoto.utils.BitmapUtil;

class EditorVignette {
    private static final int SWEEP_ANGLE = 8;

    private float mFeather = 0.7f;

    private float mArcDistance = 10;
    private float mControlPointSize = 12;
    private float mControlPointTolerance = 20;
    private float mGradientInset = 100;

    private Paint mShaderPaint;
    private Paint mVignettePaint;
    private Paint mVignetteControlPaint;
    private Paint mControlPointPaint;

    private RectF mTempRect;
    private RectF mVignetteControlRect;
    private RectF mVignetteRect;

    private Matrix mGradientMatrix;

    private RadialGradient mRadialGradient;

    private Context mContext;

    //Todo: Add fade animation;
    EditorVignette(Context context) {
        mContext = context;
        initialize();
    }

    private void initialize() {
        mGradientMatrix = new Matrix();

        mTempRect = new RectF();
        mVignetteControlRect = new RectF();

        mVignetteControlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVignetteControlPaint.setColor(Color.BLACK);
        mVignetteControlPaint.setStrokeWidth(BitmapUtil.dp2px(mContext, 0.75f));
        mVignetteControlPaint.setStyle(Paint.Style.STROKE);
        mVignetteControlPaint.setDither(true);

        mControlPointPaint = new Paint(mVignetteControlPaint);
        mControlPointPaint.setStrokeWidth(BitmapUtil.dp2px(mContext, 1.5f));

        mVignettePaint = new Paint();
        mVignettePaint.setAntiAlias(true);
        mVignettePaint.setFilterBitmap(false);
        mVignettePaint.setDither(true);

        mGradientMatrix = new Matrix();
        mVignetteRect = new RectF();

        updateBackgroundMask(15);

        mShaderPaint = new Paint();
        mShaderPaint.setAntiAlias(true);
        mShaderPaint.setFilterBitmap(false);
        mShaderPaint.setDither(true);
        mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        updateGradientShader(0.7f, mShaderPaint);


        mControlPointSize = BitmapUtil.dp2px(mContext, 4);
        mControlPointTolerance = mControlPointTolerance * 1.5f;

        mArcDistance = BitmapUtil.dp2px(mContext, 3);
        mGradientInset = BitmapUtil.dp2px(mContext, 0);
    }

    public void setVignetteFeather(float value) {
        updateGradientShader(value, mShaderPaint);
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

    private void updateGradientMatrix(RectF rect) {
        mGradientMatrix.reset();
        mGradientMatrix.postTranslate(rect.centerX(), rect.centerY());
        mGradientMatrix.postScale(rect.width() / 2, rect.height() / 2, rect.centerX(), rect.centerY());
        mRadialGradient.setLocalMatrix(mGradientMatrix);
    }

    private void updateBackgroundMask(int value) {
        if (value >= 0) {
            mVignettePaint.setColor(Color.BLACK);
        } else {
            mVignettePaint.setColor(Color.WHITE);
        }

        value = Math.max(Math.min(Math.abs(value), 100), 0);
        value *= 2.55;
        mVignettePaint.setAlpha(value);
    }

    void draw(Canvas canvas, RectF bitmapRect, Paint bitmapPaint) {
        if (!mVignetteRect.isEmpty()) {
            canvas.saveLayer(bitmapRect, bitmapPaint, Canvas.ALL_SAVE_FLAG);

            mVignetteControlRect.set(mVignetteRect);
            mVignetteControlRect.inset(-mGradientInset, -mGradientInset);


            canvas.drawRect(bitmapRect, mVignettePaint);
            canvas.drawOval(mVignetteControlRect, mShaderPaint);
            canvas.restore();

            mVignetteControlRect.set(mVignetteRect);
            mVignetteControlRect.inset(-mArcDistance, -mArcDistance);

            canvas.drawOval(mVignetteRect, mVignetteControlPaint);

            mControlPointPaint.setStyle(Paint.Style.STROKE);

            canvas.drawCircle(
                    mVignetteRect.centerX(),
                    mVignetteRect.centerY(),
                    mControlPointSize,
                    mControlPointPaint
            );

            canvas.drawArc(mVignetteControlRect, -SWEEP_ANGLE / 2, SWEEP_ANGLE, false, mControlPointPaint);
            canvas.drawArc(mVignetteControlRect, 90 - SWEEP_ANGLE / 2, SWEEP_ANGLE, false, mControlPointPaint);
            canvas.drawArc(mVignetteControlRect, 180 - SWEEP_ANGLE / 2, SWEEP_ANGLE, false, mControlPointPaint);
            canvas.drawArc(mVignetteControlRect, 270 - SWEEP_ANGLE / 2, SWEEP_ANGLE, false, mControlPointPaint);

            mVignetteControlRect.inset(mArcDistance * 2, mArcDistance * 2);

            canvas.drawArc(mVignetteControlRect, -SWEEP_ANGLE / 2, SWEEP_ANGLE, false, mControlPointPaint);
            canvas.drawArc(mVignetteControlRect, 90 - SWEEP_ANGLE / 2, SWEEP_ANGLE, false, mControlPointPaint);
            canvas.drawArc(mVignetteControlRect, 180 - SWEEP_ANGLE / 2, SWEEP_ANGLE, false, mControlPointPaint);
            canvas.drawArc(mVignetteControlRect, 270 - SWEEP_ANGLE / 2, SWEEP_ANGLE, false, mControlPointPaint);

            float rad = (float) Math.toRadians(45);

            final float radiusX = mVignetteRect.width() / 2 * (float) Math.cos(rad);
            final float radiusY = mVignetteRect.height() / 2 * (float) Math.sin(rad);

            mControlPointPaint.setStyle(Paint.Style.FILL);

            canvas.drawRect(
                    mVignetteRect.centerX() - radiusX - mControlPointSize,
                    mVignetteRect.centerY() - radiusY - mControlPointSize,
                    mVignetteRect.centerX() - radiusX + mControlPointSize,
                    mVignetteRect.centerY() - radiusY + mControlPointSize,
                    mControlPointPaint
            );

            canvas.drawRect(
                    mVignetteRect.centerX() + radiusX - mControlPointSize,
                    mVignetteRect.centerY() - radiusY - mControlPointSize,
                    mVignetteRect.centerX() + radiusX + mControlPointSize,
                    mVignetteRect.centerY() - radiusY + mControlPointSize,
                    mControlPointPaint
            );

            canvas.drawRect(
                    mVignetteRect.centerX() + radiusX - mControlPointSize,
                    mVignetteRect.centerY() + radiusY - mControlPointSize,
                    mVignetteRect.centerX() + radiusX + mControlPointSize,
                    mVignetteRect.centerY() + radiusY + mControlPointSize,
                    mControlPointPaint
            );

            canvas.drawRect(
                    mVignetteRect.centerX() - radiusX - mControlPointSize,
                    mVignetteRect.centerY() + radiusY - mControlPointSize,
                    mVignetteRect.centerX() - radiusX + mControlPointSize,
                    mVignetteRect.centerY() + radiusY + mControlPointSize,
                    mControlPointPaint
            );
        }
    }
}
