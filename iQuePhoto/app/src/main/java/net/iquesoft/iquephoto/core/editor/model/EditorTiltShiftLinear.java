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
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.model.EditorTiltShift;
import net.iquesoft.iquephoto.util.BitmapUtil;

// TODO: Linear tilt shift.
public class EditorTiltShiftLinear implements EditorTiltShift {
    private float mFeather = 0.7f;
    private float mGradientInset = 100;
    private float mControlPointTolerance = 20;

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

        mTiltShiftLinearRect = new RectF();
        mTempTiltShiftLinearRect = new RectF();
        mTiltShiftLinearControlRect = new RectF();

        mShaderPaint = new Paint();
        mShaderPaint.setAntiAlias(true);
        mShaderPaint.setFilterBitmap(false);
        mShaderPaint.setDither(true);
        mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    @Override
    public void draw(Canvas canvas, Bitmap bitmap, Matrix matrix, Paint paint) {
        if (!mTiltShiftLinearRect.isEmpty()) {

            canvas.drawBitmap(bitmap, matrix, paint);

            canvas.saveLayer(mBitmapRect, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            mTiltShiftLinearControlRect.set(mTiltShiftLinearRect);
            mTiltShiftLinearControlRect.inset(-mGradientInset, -mGradientInset);

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

            mTiltShiftLinearControlRect.set(mTiltShiftLinearRect);

            canvas.drawRect(mTiltShiftLinearRect, mTiltShiftLinearControlPaint);
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

                    mTiltShiftLinearRect.inset(-(rect.width() - old_width) / 2, -(rect.height() - old_height) / 2);
                    mTiltShiftLinearRect.offset(rect.left - old_left, rect.top - old_top);
                    mTiltShiftLinearRect.offset((rect.width() - old_width) / 2, (rect.height() - old_height) / 2);
                } else {
                    mTiltShiftLinearRect.set(rect);
                    mTiltShiftLinearRect.inset(mControlPointTolerance, mControlPointTolerance);
                }
            }
            mBitmapRect.set(rect);
        } else {
            mBitmapRect.setEmpty();
            mTiltShiftLinearRect.setEmpty();
        }

        updateGradientMatrix(mTiltShiftLinearRect);

        setPaintAlpha(125);
        //mFadeOutAnimator.start();
    }

    @Override
    public void updateGradientRect() {
        final int[] colors = new int[]{0xff000000, 0xff000000, 0};
        final float[] anchors = new float[]{0, mFeather, 1};

        /*mLinearGradient = new android.graphics.LinearGradient(
                0, 0, 1, colors, anchors, Shader.TileMode.CLAMP
        );*/

        updateGradientMatrix(mTiltShiftLinearRect);
    }

    @Override
    public void updateGradientShader(float value, Paint paint) {

    }

    @Override
    public void updateGradientMatrix(RectF rectF) {
        mGradientMatrix.reset();
        mGradientMatrix.postTranslate(rectF.centerX(), rectF.centerY());
        mGradientMatrix.postScale(rectF.height() / 2, rectF.height() / 2, rectF.centerX(), rectF.centerY());
        mLinearGradient.setLocalMatrix(mGradientMatrix);
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

    @Override
    public void actionUp() {

    }

    @Override
    public void actionPointerUp() {

    }

    @Override
    public void setPaintAlpha(int value) {

    }

    @Override
    public int getPaintAlpha() {
        return 0;
    }
}
