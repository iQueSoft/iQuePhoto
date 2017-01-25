package net.iquesoft.iquephoto.core.editor.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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

import static net.iquesoft.iquephoto.core.editor.enums.EditorMode.MOVE;
import static net.iquesoft.iquephoto.core.editor.enums.EditorMode.NONE;
import static net.iquesoft.iquephoto.core.editor.enums.EditorMode.ROTATE_AND_SCALE;

// TODO: Linear tilt shift.
// TODO: Rotate linear tilt shift.
public class EditorTiltShiftLinear implements EditorTiltShift {
    private float mFeather = 0.7f;
    private float mGradientInset = 100;
    private float mFocusHeight;
    private float mControlPointTolerance = 20;
    private float mRotateAngle;

    private float mPreX;
    private float mPreY;

    private float mPreDistance;

    private Context mContext;

    private Paint mPaint;
    private Paint mShaderPaint;
    private Paint mTiltShiftLinearPaint;
    private Paint mTiltShiftLinearControlPaint;

    private Bitmap mBitmap;
    private Bitmap mBlurBitmap;

    private RectF mBitmapRect;
    private RectF mTiltShiftLinearRect;
    private RectF mTempTiltShiftLinearRect;
    private RectF mTiltShiftLinearControlRect;

    private Matrix mGradientMatrix;

    private LinearGradient mLinearGradient;

    private EditorMode mMode = NONE;

    private ImageEditorView mImageEditorView;

    public EditorTiltShiftLinear(ImageEditorView imageEditorView) {
        mImageEditorView = imageEditorView;

        mContext = imageEditorView.getContext();

        initialize(mContext);
    }

    @Override
    public void initialize(Context context) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        mPaint = new Paint();

        mTiltShiftLinearControlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTiltShiftLinearControlPaint.setColor(Color.WHITE);
        mTiltShiftLinearControlPaint.setStyle(Paint.Style.STROKE);
        mTiltShiftLinearControlPaint.setStrokeWidth(metrics.density * 3.5f);
        mTiltShiftLinearControlPaint.setAlpha(125);
        mTiltShiftLinearControlPaint.setDither(true);

        mTiltShiftLinearPaint = new Paint();
        mTiltShiftLinearPaint.setAntiAlias(true);
        mTiltShiftLinearPaint.setFilterBitmap(false);
        mTiltShiftLinearPaint.setDither(true);

        mGradientMatrix = new Matrix();

        mBitmapRect = new RectF();
        mTiltShiftLinearRect = new RectF();
        mTempTiltShiftLinearRect = new RectF();
        mTiltShiftLinearControlRect = new RectF();

        mShaderPaint = new Paint();
        mShaderPaint.setAntiAlias(true);
        mShaderPaint.setFilterBitmap(false);
        mShaderPaint.setDither(true);
        mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        updateGradientRect();

        mControlPointTolerance *= 1.5f;
        mGradientInset = 0;
    }

    @Override
    public void draw(Canvas canvas, Bitmap bitmap, Matrix matrix, Paint paint) {
        if (!mTiltShiftLinearRect.isEmpty()) {

            canvas.drawBitmap(bitmap, matrix, paint);

            canvas.saveLayer(mBitmapRect, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            mTiltShiftLinearControlRect.set(mTiltShiftLinearRect);
            //mTiltShiftLinearControlRect.inset(-mGradientInset, -mGradientInset);

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

            canvas.drawRect(mTiltShiftLinearControlRect, mShaderPaint);

            canvas.restore();

            //mTiltShiftLinearControlRect.set(mTiltShiftLinearRect);

            canvas.drawRect(mTiltShiftLinearControlRect, mTiltShiftLinearControlPaint);

            /* TODO: Line for linear tilt shift
            canvas.drawLine(
                    mTiltShiftLinearControlRect.left,
                    mTiltShiftLinearControlRect.top,
                    mTiltShiftLinearControlRect.left + mTiltShiftLinearControlRect.width(),
                    mTiltShiftLinearControlRect.top + mTiltShiftLinearControlRect.width(),
                    mTiltShiftLinearControlPaint
            );

            canvas.drawLine(
                    mTiltShiftLinearControlRect.right - mTiltShiftLinearControlRect.width(),
                    mTiltShiftLinearControlRect.bottom - mTiltShiftLinearControlRect.width(),
                    mTiltShiftLinearControlRect.right,
                    mTiltShiftLinearControlRect.bottom,
                    mTiltShiftLinearControlPaint
            );*/


        }
    }

    @Override
    public void updateRect(RectF bitmapRect) {
        mFocusHeight = bitmapRect.height() / 2;

        mTiltShiftLinearRect.set(
                bitmapRect.left,
                bitmapRect.centerY() - mFocusHeight / 2,
                bitmapRect.right,
                bitmapRect.centerY() + mFocusHeight / 2);

        mBitmapRect.set(bitmapRect);

        updateGradientMatrix(mTiltShiftLinearRect);

        setPaintAlpha(125);
    }

    @Override
    public void updateGradientRect() {
        final float[] anchors = new float[]{0, mFeather, 1};

        float start = 0;
        float bottom = 0;

        mLinearGradient = new android.graphics.LinearGradient(
                0, start, 0, bottom, 0xffffffff, 0x00ffffff, Shader.TileMode.CLAMP
        );

        updateGradientMatrix(mTiltShiftLinearRect);
    }

    @Override
    public void updateGradientShader(float value, Paint paint) {

    }

    // TODO: Check gradient matrix.
    @Override
    public void updateGradientMatrix(RectF rectF) {
        mGradientMatrix.reset();
        mGradientMatrix.postTranslate(rectF.centerX(), rectF.centerY());
        mGradientMatrix.postScale(rectF.height() / 2, rectF.height() / 2, rectF.centerX(), rectF.centerY());
        mLinearGradient.setLocalMatrix(mGradientMatrix);
    }

    @Override
    public void actionMove(MotionEvent event) {
        mTempTiltShiftLinearRect.set(mTiltShiftLinearRect);

        switch (mMode) {
            case MOVE:
                float distanceX = event.getX() - mPreX;
                float distanceY = event.getY() - mPreY;

                mTempTiltShiftLinearRect.offsetTo(distanceX, distanceY);
                break;
            case ROTATE_AND_SCALE:
                float dist = MotionEventUtil.getDelta(event);
                float scale = ((dist - mPreDistance) / displayDistance());

                mPreDistance = dist;

                scale += 1;
                scale *= scale;

                RectUtil.scaleRect(mTempTiltShiftLinearRect, scale);

                mFocusHeight = mTempTiltShiftLinearRect.height();
                break;
        }

        if (mTempTiltShiftLinearRect.width() > mControlPointTolerance
                && mTempTiltShiftLinearRect.height() > mControlPointTolerance) {
            if (isTiltShiftInRect()) {
                mTiltShiftLinearRect.set(mTempTiltShiftLinearRect);

                mPreX = event.getX();
                mPreY = event.getY();
            }

            updateGradientMatrix(mTiltShiftLinearRect);

            mImageEditorView.invalidate();

            ViewCompat.postInvalidateOnAnimation(mImageEditorView);
        }
    }

    @Override
    public void actionDown(MotionEvent event) {
        mPreX = event.getX();
        mPreY = event.getY();

        mMode = MOVE;
    }

    @Override
    public void actionPointerDown(MotionEvent event) {
        mMode = ROTATE_AND_SCALE;
    }

    @Override
    public void actionUp() {
        mMode = NONE;
    }

    @Override
    public void actionPointerUp() {
        mMode = NONE;
    }

    @Override
    public void setPaintAlpha(int value) {

    }

    @Override
    public int getPaintAlpha() {
        return 0;
    }

    private float displayDistance() {

        float height = mImageEditorView.getHeight();
        float width = mImageEditorView.getWidth();

        return (float) Math.sqrt(width * width + height * height);
    }

    private boolean isTiltShiftInRect() {
        return mBitmapRect.contains(
                mTempTiltShiftLinearRect.centerX(),
                mTempTiltShiftLinearRect.centerY()
        );
    }
}
