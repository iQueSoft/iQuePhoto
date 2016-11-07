package net.iquesoft.iquephoto.core;

import android.graphics.Canvas;
import android.graphics.Color;
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

    private int mX = 150;
    private int mY = 150;

    private int mTextRectWidth;

    private float mRotateDegree = 0;

    private boolean mIsInEdit;

    private Typeface mTypeface;

    private TextPaint mTextPaint;

    private Rect mTextRect;

    private Rect mRotateHandleSrcRect;
    private Rect mResizeHandleSrcRect;
    private Rect mDeleteHandleSrcRect;
    private Rect mFrontHandleSrcRect;

    private RectF mFrameRect;
    private RectF mDeleteHandleDstRect;
    private RectF mRotateHandleDstRect;
    private RectF mResizeHandleDstRect;
    private RectF mFrontHandleDstRect;

    private EditorFrame mEditorFrame;

    EditorText(String text, @Nullable Typeface typeface, int color, int opacity, EditorFrame editorFrame) {
        mText = text;

        if (typeface != null)
            mTypeface = typeface;
        else
            mTypeface = Typeface.DEFAULT;

        mColor = color;
        mOpacity = 255;

        mEditorFrame = editorFrame;

        initTextPaint();
        initEditorText();
    }

    private void initEditorText() {
        mTextRect = new Rect();
        mFrameRect = new RectF();

        mRotateHandleSrcRect = new Rect(0, 0, mEditorFrame.getDeleteHandleBitmap().getWidth(),
                mEditorFrame.getDeleteHandleBitmap().getHeight());
        mDeleteHandleSrcRect = new Rect(0, 0, mEditorFrame.getResizeHandleBitmap().getWidth(),
                mEditorFrame.getResizeHandleBitmap().getHeight());
        mResizeHandleSrcRect = new Rect(0, 0, mEditorFrame.getRotateHandleBitmap().getWidth(),
                mEditorFrame.getRotateHandleBitmap().getHeight());
        mFrontHandleSrcRect = new Rect(0, 0, mEditorFrame.getFrontHandleBitmap().getWidth(),
                mEditorFrame.getFrontHandleBitmap().getHeight());

        int handleHalfSize = mEditorFrame.getDeleteHandleBitmap().getWidth() / 2;

        mDeleteHandleDstRect = new RectF(0, 0, handleHalfSize << 1, handleHalfSize << 1);
        mResizeHandleDstRect = new RectF(0, 0, handleHalfSize << 1, handleHalfSize << 1);
        mFrontHandleDstRect = new RectF(0, 0, handleHalfSize << 1, handleHalfSize << 1);
        mRotateHandleDstRect = new RectF(0, 0, handleHalfSize << 1, handleHalfSize << 1);
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

    void drawText(Canvas canvas) {

        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);

        mTextRect.offset(mX - (mTextRect.width() >> 1), mY);

        mFrameRect.set(mTextRect.left - PADDING, mTextRect.top - PADDING,
                mTextRect.right + PADDING, mTextRect.bottom + PADDING);

        canvas.drawText(mText, mX, mY, mTextPaint);

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

        canvas.drawRect(mFrameRect, mEditorFrame.getPaint());

        canvas.drawBitmap(mEditorFrame.getDeleteHandleBitmap(),
                mDeleteHandleSrcRect, mDeleteHandleDstRect, null);
        canvas.drawBitmap(mEditorFrame.getRotateHandleBitmap(),
                mRotateHandleSrcRect, mRotateHandleDstRect, null);
        canvas.drawBitmap(mEditorFrame.getResizeHandleBitmap(),
                mResizeHandleSrcRect, mResizeHandleDstRect, null);
        canvas.drawBitmap(mEditorFrame.getFrontHandleBitmap(),
                mFrontHandleSrcRect, mFrontHandleDstRect, null);
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

    int getTextRectWidth() {
        return mTextRect.width();
    }
}
