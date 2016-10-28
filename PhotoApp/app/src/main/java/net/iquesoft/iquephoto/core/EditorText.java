package net.iquesoft.iquephoto.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;

import net.iquesoft.iquephoto.utils.RectUtil;

public class EditorText {
    static final int PADDING = 25;
    static final int DEFAULT_COLOR = Color.BLACK;
    static final int DEFAULT_OPACITY = 255;
    static final float DEFAULT_SIZE = 80;

    private String mText;

    private int mColor;
    private int mOpacity;

    private int mX = 300;
    private int mY = 300;

    private float mRotateDegree = 0;

    private boolean mIsInEdit;

    private Typeface mTypeface;

    private TextPaint mTextPaint;

    private Rect mTextRect;

    private RectF mFrameRect;
    private RectF mDeleteHandleDstRect;
    private RectF mRotateHandleDstRect;
    private RectF mResizeHandleDstRect;
    private RectF mFrontHandleDstRect;

    private Rect mRotateHandleSrcRect;
    private Rect mResizeHandleSrcRect;
    private Rect mDeleteHandleSrcRect;
    private Rect mFrontHandleSrcRect;

    private Matrix mMatrix;

    public EditorText(String text, @Nullable Typeface typeface, int color, int opacity) {
        mText = text;

        if (typeface != null)
            mTypeface = typeface;
        else
            mTypeface = Typeface.DEFAULT;

        mColor = color;
        mOpacity = 255;

        initTextPaint();
        initEditorText();
    }

    private void initEditorText() {

        mMatrix = new Matrix();

        mTextRect = new Rect();
        mFrameRect = new RectF();

        mRotateHandleSrcRect = new Rect();
        mDeleteHandleSrcRect = new Rect();
        mResizeHandleSrcRect = new Rect();
        mFrontHandleSrcRect = new Rect();

        mDeleteHandleDstRect = new RectF(0, 0, 30 << 1, 30 << 1);
        mResizeHandleDstRect = new RectF(0, 0, 30 << 1, 30 << 1);
        mFrontHandleDstRect = new RectF(0, 0, 30 << 1, 30 << 1);
        mRotateHandleDstRect = new RectF(0, 0, 30 << 1, 30 << 1);
    }

    private void initTextPaint() {
        mTextPaint = new TextPaint();

        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(mOpacity);

        mTextPaint.setTextSize(DEFAULT_SIZE);
        mTextPaint.setTypeface(mTypeface);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    void drawText(Canvas canvas, Bitmap deleteHandleBitmap, Bitmap rotateHandleBitmap, Bitmap resizeHandleBitmap,
                  Bitmap frontHandleBitmap, Paint framePaint) {

        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        mTextRect.offset(mX - (mTextRect.width() >> 1), mY);

        mFrameRect.set(mTextRect.left - PADDING, mTextRect.top - PADDING,
                mTextRect.right + PADDING, mTextRect.bottom + PADDING);

        canvas.drawText(mText, mX, mY, mTextPaint);

        mDeleteHandleSrcRect.set(0, 0, deleteHandleBitmap.getWidth(), deleteHandleBitmap.getHeight());
        mResizeHandleSrcRect.set(0, 0, resizeHandleBitmap.getWidth(), resizeHandleBitmap.getHeight());
        mFrontHandleSrcRect.set(0, 0, frontHandleBitmap.getWidth(), frontHandleBitmap.getHeight());
        mRotateHandleSrcRect.set(0, 0, rotateHandleBitmap.getWidth(), rotateHandleBitmap.getHeight());

        int offsetValue = ((int) mDeleteHandleDstRect.width()) >> 1;

        mDeleteHandleDstRect.offset(mFrameRect.left - offsetValue, mFrameRect.top - offsetValue);
        mResizeHandleDstRect.offset(mFrameRect.right - offsetValue, mFrameRect.bottom - offsetValue);
        mRotateHandleDstRect.offset(mFrameRect.right - offsetValue, mFrameRect.top - offsetValue);
        mFrontHandleDstRect.offset(mFrameRect.left - offsetValue, mFrameRect.bottom - offsetValue);

        RectUtil.rotateRect(mDeleteHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateDegree);

        RectUtil.rotateRect(mRotateHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateDegree);

        RectUtil.rotateRect(mResizeHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateDegree);

        RectUtil.rotateRect(mFrontHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateDegree);

        canvas.drawRect(mFrameRect, framePaint);

        canvas.drawBitmap(deleteHandleBitmap, mDeleteHandleSrcRect, mDeleteHandleDstRect, null);
        canvas.drawBitmap(rotateHandleBitmap, mRotateHandleSrcRect, mRotateHandleDstRect, null);
        canvas.drawBitmap(resizeHandleBitmap, mResizeHandleSrcRect, mResizeHandleDstRect, null);
        canvas.drawBitmap(frontHandleBitmap, mFrontHandleSrcRect, mFrontHandleDstRect, null);

    }

    void setX(int x) {
        mX = x;
    }

    void setY(int y) {
        mY = y;
    }

    int getX() {
        return mX;
    }

    int getY() {
        return mY;
    }

    RectF getFrameRect() {
        return mFrameRect;
    }

    RectF getDeleteHandleDstRect() {
        return mDeleteHandleDstRect;
    }

    RectF getRotateHandleDstRect() {
        return mRotateHandleDstRect;
    }

    Matrix getMatrix() {
        return mMatrix;
    }

    Rect getDeleteHandleRect() {
        return mDeleteHandleSrcRect;
    }

    Rect getResizeHandleRect() {
        return mResizeHandleSrcRect;
    }

    Rect getRotateHandleRect() {
        return mRotateHandleSrcRect;
    }

    Rect getFrontHandleRect() {
        return mFrontHandleSrcRect;
    }

    void setIsInEdit(boolean isInEdit) {
        mIsInEdit = isInEdit;
    }

    void setRotateDegree(float rotateDegree) {
        mRotateDegree = rotateDegree;
    }

    float getRotateDegree() {
        return mRotateDegree;
    }

    boolean isInEdit() {
        return mIsInEdit;
    }
}
