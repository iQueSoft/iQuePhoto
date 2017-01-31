package net.iquesoft.iquephoto.core.editor.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;

import net.iquesoft.iquephoto.core.editor.enums.EditorMode;
import net.iquesoft.iquephoto.utils.LogHelper;
import net.iquesoft.iquephoto.utils.MatrixUtil;
import net.iquesoft.iquephoto.utils.MotionEventUtil;
import net.iquesoft.iquephoto.utils.RectUtil;

import static net.iquesoft.iquephoto.core.editor.enums.EditorMode.*;

public class EditorVignette {
    private static final String TAG = "Vignette";
    private static final int FADEOUT_DELAY = 3000;

    private float mFeather = 0.7f;

    private float mPreX;
    private float mPreY;

    private int mViewWidth;
    private int mViewHeight;

    private float mPreDistance;

    private float mGradientInset = 100;
    private float mControlPointTolerance = 20;

    private boolean mIsShowHelpOval = true;

    private Paint mPaint = new Paint();
    private Paint mShaderPaint;
    private Paint mVignettePaint;
    private Paint mVignetteControlPaint;

    private RectF mBitmapRect = new RectF();
    private RectF mVignetteRect;
    private RectF mTempVignetteRect = new RectF();
    private RectF mVignetteControlRect = new RectF();

    private RadialGradient mRadialGradient;

    private Matrix mGradientMatrix;

    private EditorMode mMode = NONE;

    public EditorVignette(int viewWidth, int viewHeight) {
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;

        initializeVignette();
    }

    private void initializeVignette() {
        mVignetteControlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVignetteControlPaint.setColor(Color.WHITE);
        mVignetteControlPaint.setStrokeWidth(5f);
        mVignetteControlPaint.setStyle(Paint.Style.STROKE);
        mVignetteControlPaint.setAlpha(125);
        mVignetteControlPaint.setDither(true);
        mVignetteControlPaint.setPathEffect(
                new DashPathEffect(
                        new float[]{10, 20}, 0)
        );

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

        updateGradientShader(mFeather, mShaderPaint);

        mControlPointTolerance = mControlPointTolerance * 1.5f;

        mGradientInset = 0;
    }

    public void updateMask(int value) {
        if (value >= 0) {
            mVignettePaint.setColor(Color.BLACK);
        } else {
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

    private void updateGradientMatrix(RectF rectF) {
        mGradientMatrix.reset();
        mGradientMatrix.postTranslate(rectF.centerX(), rectF.centerY());
        mGradientMatrix.postScale(rectF.width() / 2, rectF.height() / 2, rectF.centerX(), rectF.centerY());
        mRadialGradient.setLocalMatrix(mGradientMatrix);
    }

    /**
     * Reset the Vignette Rect when Tool changed.
     */
    public void updateRect(RectF bitmapRect) {
        mVignetteRect.set(bitmapRect);
        mVignetteRect.inset(mControlPointTolerance, mControlPointTolerance);

        mBitmapRect.set(bitmapRect);

        Log.i(TAG, "Is Reset!");

        updateGradientMatrix(mVignetteRect);
    }

    /**
     * @param canvas is a view canvas if you want to draw on image canvas call prepareToDraw().
     */
    public void draw(@NonNull Canvas canvas) {
        if (!mVignetteRect.isEmpty()) {
            canvas.saveLayer(mBitmapRect, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            mVignetteControlRect.set(mVignetteRect);
            mVignetteControlRect.inset(-mGradientInset, -mGradientInset);

            canvas.drawRect(mBitmapRect, mVignettePaint);
            canvas.drawOval(mVignetteControlRect, mShaderPaint);

            canvas.restore();

            if (mIsShowHelpOval) {
                mVignetteControlRect.inset(50, 50);
                canvas.drawOval(mVignetteControlRect, mVignetteControlPaint);
            }
        }
    }

    public void actionDown(MotionEvent motionEvent) {
        mMode = MOVE;

        Log.i(TAG, "Action: Down \n" +
                "Mode: " + mMode.name());

        mPreX = motionEvent.getX();
        mPreY = motionEvent.getY();
    }

    public void actionPointerDown(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            float angle = MotionEventUtil.getAngle(event);

            if (angle < 0) {
                angle += 180;
            }

            if (angle > 36 && angle < 72 || angle > 108 && angle < 144) {
                mPreDistance = MotionEventUtil.getDelta(event);
                mMode = ROTATE_AND_SCALE;
            } else if (angle >= 72 && angle <= 108) {
                mMode = RESIZE_HEIGHT;
            } else {
                mMode = RESIZE_WIDTH;
            }

            Log.i(TAG, "Action: Pointer Down" + "\n"
                    + "Angle: " + String.valueOf(angle) + "\n"
                    + "Mode: " + mMode.name());
        }
    }

    public void actionMove(MotionEvent event) {
        mTempVignetteRect.set(mVignetteRect);

        float distanceX = event.getX() - mPreX;
        float distanceY = event.getY() - mPreY;

        Log.i(TAG, "Action: Move \n" +
                "Mode: " + mMode.name());

        switch (mMode) {
            case MOVE:
                mTempVignetteRect.offset(distanceX, distanceY);
                break;
            case ROTATE_AND_SCALE:
                float dist = MotionEventUtil.getDelta(event);
                float displayDistance = MotionEventUtil.getDisplayDistance(
                        mViewWidth,
                        mViewHeight
                );

                float scale = ((dist - mPreDistance) / displayDistance);

                mPreDistance = dist;

                scale += 1;
                scale *= scale;

                RectUtil.scaleRect(mTempVignetteRect, scale);
                break;
            case RESIZE_HEIGHT:
                mTempVignetteRect.inset(0, distanceY);
                break;
            case RESIZE_WIDTH:
                mTempVignetteRect.inset(-distanceX, 0);
                break;
        }

        if (mTempVignetteRect.width() > mControlPointTolerance
                && mTempVignetteRect.height() > mControlPointTolerance) {
            if (isVignetteInRect()) {
                mVignetteRect.set(mTempVignetteRect);

                mPreX = event.getX();
                mPreY = event.getY();
            }

            updateGradientMatrix(mVignetteRect);
        }
    }

    public void actionUp() {
        mMode = NONE;
        Log.i(TAG, "Action: Up \n" +
                "Mode: " + mMode.name());
    }

    public void actionPointerUp() {
        mMode = MOVE;
        Log.i(TAG, "Action: Pointer Up \n" +
                "Mode: " + mMode.name());
    }

    public void prepareToDraw(@NonNull Canvas canvas, @NonNull Matrix matrix) {
        LogHelper.logRect("Vignette - before", mVignetteRect);

        float scale = MatrixUtil.getScale(matrix);

        float x = MatrixUtil.getMatrixX(matrix);
        float y = MatrixUtil.getMatrixY(matrix);

        float newLeft = (mVignetteRect.left - x) / scale;
        float newTop = (mVignetteRect.top - y) / scale;

        mVignetteRect.offsetTo(newLeft, newTop);

        mVignetteRect.right /= scale;
        mVignetteRect.bottom /= scale;

        updateGradientMatrix(mVignetteRect);

        mIsShowHelpOval = false;

        mBitmapRect.set(0, 0, canvas.getWidth(), canvas.getHeight()); // To save layer.

        LogHelper.logRect("Vignette - after", mVignetteRect);
    }

    private boolean isVignetteInRect() {
        return mBitmapRect.contains(
                mTempVignetteRect.centerX(),
                mTempVignetteRect.centerY()
        );
    }
}