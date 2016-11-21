package net.iquesoft.iquephoto.core.editor.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;

import net.iquesoft.iquephoto.model.Text;
import net.iquesoft.iquephoto.util.RectUtil;

import static net.iquesoft.iquephoto.core.editor.model.EditorFrame.EDITOR_FRAME_PADDING;

public class EditorText {

    static final int DEFAULT_COLOR = Color.BLACK;
    private static final float DEFAULT_TEXT_SIZE = 80;

    private String mText;

    private int mColor;
    private int mOpacity;

    private int mX;
    private int mY;

    private float mScale = 1;
    private float mRotateAngle = 0;

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

    public EditorText(Text text, EditorFrame editorFrame) {
        mText = text.getText();

        mTypeface = text.getTypeface();

        mColor = text.getColor();
        mOpacity = text.getOpacity();

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

        mTextPaint.setTextSize(DEFAULT_TEXT_SIZE);
        mTextPaint.setTypeface(mTypeface);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void draw(Canvas canvas) {

        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);

        // TODO: Check if can change this method to offsetTo() method.
        mTextRect.offset(mX - (mTextRect.width() >> 1), mY);

        mFrameRect.set(mTextRect.left - EDITOR_FRAME_PADDING, mTextRect.top - EDITOR_FRAME_PADDING,
                mTextRect.right + EDITOR_FRAME_PADDING, mTextRect.bottom + EDITOR_FRAME_PADDING);
        RectUtil.scaleRect(mFrameRect, mScale);

        canvas.save();
        canvas.scale(mScale, mScale, mFrameRect.centerX(), mFrameRect.centerY());
        canvas.rotate(mRotateAngle, mFrameRect.centerX(), mFrameRect.centerY());
        canvas.drawText(mText, mX, mY, mTextPaint);
        canvas.restore();

        // TODO: Doesn't draw EditorFrame if text does'n touched.
        int offsetValue = ((int) mDeleteHandleDstRect.width()) >> 1;

        mDeleteHandleDstRect.offsetTo(mFrameRect.left - offsetValue, mFrameRect.top - offsetValue);
        mResizeHandleDstRect.offsetTo(mFrameRect.right - offsetValue, mFrameRect.bottom - offsetValue);
        mRotateHandleDstRect.offsetTo(mFrameRect.right - offsetValue, mFrameRect.top - offsetValue);
        mFrontHandleDstRect.offsetTo(mFrameRect.left - offsetValue, mFrameRect.bottom - offsetValue);

        RectUtil.rotateRect(mDeleteHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateAngle);

        RectUtil.rotateRect(mRotateHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateAngle);

        RectUtil.rotateRect(mResizeHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateAngle);

        RectUtil.rotateRect(mFrontHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateAngle);

        canvas.save();
        canvas.rotate(mRotateAngle, mFrameRect.centerX(), mFrameRect.centerY());
        canvas.drawRect(mFrameRect, mEditorFrame.getPaint());
        canvas.restore();

        canvas.drawBitmap(mEditorFrame.getDeleteHandleBitmap(),
                mDeleteHandleSrcRect, mDeleteHandleDstRect, null);
        canvas.drawBitmap(mEditorFrame.getRotateHandleBitmap(),
                mRotateHandleSrcRect, mRotateHandleDstRect, null);
        canvas.drawBitmap(mEditorFrame.getResizeHandleBitmap(),
                mResizeHandleSrcRect, mResizeHandleDstRect, null);
        canvas.drawBitmap(mEditorFrame.getFrontHandleBitmap(),
                mFrontHandleSrcRect, mFrontHandleDstRect, null);
    }

    public void setX(int x) {
        mX = x;
    }

    public void setY(int y) {
        mY = y;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public RectF getFrameRect() {
        return mFrameRect;
    }

    public RectF getDeleteHandleDstRect() {
        return mDeleteHandleDstRect;
    }

    public RectF getRotateHandleDstRect() {
        return mRotateHandleDstRect;
    }

    public RectF getResizeHandleDstRect() {
        return mResizeHandleDstRect;
    }

    public RectF getFrontHandleDstRect() {
        return mFrontHandleDstRect;
    }

    public void setIsInEdit(boolean isInEdit) {
        mIsInEdit = isInEdit;
    }

    public float getRotateDegree() {
        return mRotateAngle;
    }

    public boolean isInEdit() {
        return mIsInEdit;
    }

    public void rotateText(float distanceX, float distanceY) {
        float frameX = mFrameRect.centerX();
        float frameY = mFrameRect.centerY();

        float rotateX = mResizeHandleDstRect.centerX();
        float rotateY = mResizeHandleDstRect.centerY();

        float newX = rotateX + distanceX;
        float newY = rotateY + distanceY;

        float x = rotateX - frameX;
        float y = rotateY - frameY;
        float x1 = newX - frameX;
        float y1 = newY - frameY;

        float sourceLength = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        float currentLength = (float) Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2));

        double cos = (x * x1 + y * y1) / (sourceLength * currentLength);
        if (cos > 1 || cos < -1)
            return;

        float angle = (float) Math.toDegrees(Math.acos(cos));
        float calMatrix = x * y1 - x1 * y;

        int flag = calMatrix > 0 ? 1 : -1;
        angle = flag * angle;

        mRotateAngle += angle;
    }

    public void scaleText(float distanceX, float distanceY) {
        float frameX = mFrameRect.centerX();
        float frameY = mFrameRect.centerY();

        float resizeX = mResizeHandleDstRect.centerX();
        float resizeY = mResizeHandleDstRect.centerY();

        float newX = resizeX + distanceX;
        float newY = resizeY + distanceY;

        float x = resizeX - frameX;
        float y = resizeY - frameY;
        float x1 = newX - frameX;
        float y1 = newY - frameY;

        float sourceLength = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        float currentLength = (float) Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2));

        float scale = currentLength / sourceLength;

        mScale *= scale;

        float newWidth = mFrameRect.width() * mScale;

        if (newWidth < 70) {
            mScale /= scale;
        }
    }
}
