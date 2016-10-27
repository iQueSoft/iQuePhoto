package net.iquesoft.iquephoto.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class EditorText {
    static final int DEFAULT_COLOR = Color.BLACK;
    static final int DEFAULT_OPACITY = 255;
    static final float DEFAULT_SIZE = 16;

    private float mMinScale = 0.5f;
    private float mMaxScale = 1.5f;

    private final float mMaxFontSize = 25;
    private final float mMinFontSize = 14;

    private String mText;

    private int mColor;
    private int mOpacity;

    private float mSize;
    private float mBaseline;
    private float mRotateDegree;

    private double mHalfDiagonalLength;

    private boolean mIsInEdit;

    private Typeface mTypeface;

    private TextPaint mTextPaint;

    private DisplayMetrics mDisplayMetrics;

    private Paint.FontMetrics mFontMetrics;

    private Bitmap mBitmap;

    private Canvas mCanvas;

    private PointF mPointF;

    private Rect mRect;

    private Rect mRotateHandleRect;
    private Rect mResizeHandleRect;
    private Rect mDeleteHandleRect;
    private Rect mFrontHandleRect;

    private Matrix mMatrix;

    public EditorText(String text,
                      @Nullable Typeface typeface,
                      int color,
                      int opacity,
                      DisplayMetrics displayMetrics) {

        mDisplayMetrics = displayMetrics;

        mText = text;

        if (typeface != null)
            mTypeface = typeface;
        else
            mTypeface = Typeface.DEFAULT;

        mColor = color;
        mOpacity = opacity;

        initTextPaint();
        initEditorText();


    }

    private void initEditorText() {

        createTextBitmap();

        mCanvas = new Canvas(mBitmap);

        mMatrix = new Matrix();

        mPointF = new PointF();

        mRotateHandleRect = new Rect();
        mDeleteHandleRect = new Rect();
        mResizeHandleRect = new Rect();
        mFrontHandleRect = new Rect();
    }

    private void initTextPaint() {
        mTextPaint = new TextPaint();

        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(mOpacity);

        mSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mSize, mDisplayMetrics);

        mTextPaint.setTextSize(mSize);
        mTextPaint.setTypeface(mTypeface);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mFontMetrics = mTextPaint.getFontMetrics();

        mBaseline = mFontMetrics.descent - mFontMetrics.ascent;

    }

    private Bitmap createTextBitmap() {

        return Bitmap.createBitmap((int) mBaseline, (int) mSize, Bitmap.Config.ARGB_8888);
    }

    /*private int getTextWidth() {
        return mFontMetrics.
    }*/

    void drawText(Canvas canvas, Bitmap deleteHandleBitmap, Bitmap rotateHandleBitmap, Bitmap resizeHandleBitmap,
                  Bitmap frontHandleBitmap, Paint paint) {

        mBitmap = createTextBitmap();

        mCanvas.drawText(mText, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2, mTextPaint);

        float[] arrayOfFloat = new float[9];
        mMatrix.getValues(arrayOfFloat);

        float f1 = 0.0F * arrayOfFloat[0] + 0.0F * arrayOfFloat[1] + arrayOfFloat[2];
        float f2 = 0.0F * arrayOfFloat[3] + 0.0F * arrayOfFloat[4] + arrayOfFloat[5];
        float f3 = arrayOfFloat[0] * mBitmap.getWidth() + 0.0F * arrayOfFloat[1] + arrayOfFloat[2];
        float f4 = arrayOfFloat[3] * mBitmap.getWidth() + 0.0F * arrayOfFloat[4] + arrayOfFloat[5];
        float f5 = 0.0F * arrayOfFloat[0] + arrayOfFloat[1] * mBitmap.getHeight() + arrayOfFloat[2];
        float f6 = 0.0F * arrayOfFloat[3] + arrayOfFloat[4] * mBitmap.getHeight() + arrayOfFloat[5];
        float f7 = arrayOfFloat[0] * mBitmap.getWidth() + arrayOfFloat[1] * mBitmap.getHeight() + arrayOfFloat[2];
        float f8 = arrayOfFloat[3] * mBitmap.getWidth() + arrayOfFloat[4] * mBitmap.getHeight() + arrayOfFloat[5];

        canvas.drawBitmap(mBitmap, mMatrix, null);

        mDeleteHandleRect.left = (int) (f1 - deleteHandleBitmap.getWidth() / 2);
        mDeleteHandleRect.right = (int) (f1 + deleteHandleBitmap.getWidth() / 2);
        mDeleteHandleRect.top = (int) (f2 - deleteHandleBitmap.getHeight() / 2);
        mDeleteHandleRect.bottom = (int) (f2 + deleteHandleBitmap.getHeight() / 2);

        mRotateHandleRect.left = (int) (f3 - rotateHandleBitmap.getWidth() / 2);
        mRotateHandleRect.right = (int) (f3 + rotateHandleBitmap.getWidth() / 2);
        mRotateHandleRect.top = (int) (f4 - rotateHandleBitmap.getHeight() / 2);
        mRotateHandleRect.bottom = (int) (f4 + rotateHandleBitmap.getHeight() / 2);

        mFrontHandleRect.left = (int) (f5 - frontHandleBitmap.getWidth() / 2);
        mFrontHandleRect.right = (int) (f5 + frontHandleBitmap.getWidth() / 2);
        mFrontHandleRect.top = (int) (f6 - frontHandleBitmap.getHeight() / 2);
        mFrontHandleRect.bottom = (int) (f6 + frontHandleBitmap.getHeight() / 2);

        mResizeHandleRect.left = (int) (f7 - resizeHandleBitmap.getWidth() / 2);
        mResizeHandleRect.right = (int) (f7 + resizeHandleBitmap.getWidth() / 2);
        mResizeHandleRect.top = (int) (f8 - resizeHandleBitmap.getHeight() / 2);
        mResizeHandleRect.bottom = (int) (f8 + resizeHandleBitmap.getHeight() / 2);

        if (mIsInEdit) {
            canvas.drawLine(f1, f2, f3, f4, paint);
            canvas.drawLine(f3, f4, f7, f8, paint);
            canvas.drawLine(f5, f6, f7, f8, paint);
            canvas.drawLine(f5, f6, f1, f2, paint);

            canvas.drawBitmap(deleteHandleBitmap, null, mDeleteHandleRect, null);
            canvas.drawBitmap(resizeHandleBitmap, null, mResizeHandleRect, null);
            canvas.drawBitmap(frontHandleBitmap, null, mFrontHandleRect, null);
            canvas.drawBitmap(rotateHandleBitmap, null, mRotateHandleRect, null);
        }
    }

    Matrix getMatrix() {
        return mMatrix;
    }

    Rect getDeleteHandleRect() {
        return mDeleteHandleRect;
    }

    Rect getResizeHandleRect() {
        return mResizeHandleRect;
    }

    Rect getRotateHandleRect() {
        return mRotateHandleRect;
    }

    Rect getFrontHandleRect() {
        return mFrontHandleRect;
    }

    void setIsInEdit(boolean isInEdit) {
        mIsInEdit = mIsInEdit;
    }

    void setRotateDegree(float rotateDegree) {
        mRotateDegree = rotateDegree;
    }

    float getRotateDegree() {
        return mRotateDegree;
    }

    Bitmap getBitmap() {
        return mBitmap;
    }

    boolean isInEdit() {
        return mIsInEdit;
    }
}
