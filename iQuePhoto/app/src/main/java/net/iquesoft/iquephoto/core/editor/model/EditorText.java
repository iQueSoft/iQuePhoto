package net.iquesoft.iquephoto.core.editor.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.view.MotionEvent;

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

    private boolean mIsDrawHelperFrame = true;

    private Typeface mTypeface;

    private Paint mHelperFramePaint;
    private TextPaint mTextPaint;

    private Rect mTextRect;

    private Rect mDeleteHandleSrcRect;
    private Rect mFrontHandleSrcRect;
    private Rect mTransparencyHandleSrcRect;
    private Rect mResizeAndScaleHandleSrcRect;

    private RectF mFrameRect;

    private RectF mDeleteHandleDstRect;
    private RectF mFrontHandleDstRect;
    private RectF mTransparencyHandleDstRect;
    private RectF mResizeAndScaleHandleDstRect;

    private EditorFrame mEditorFrame;

    public EditorText(Text text, EditorFrame editorFrame) {
        mText = text.getText();

        mTypeface = text.getTypeface();

        mColor = text.getColor();
        mOpacity = text.getOpacity();

        mEditorFrame = editorFrame;

        mHelperFramePaint = new Paint(mEditorFrame.getFramePaint());

        initTextPaint();
        initEditorText();
    }

    private void initEditorText() {
        mTextRect = new Rect();
        mFrameRect = new RectF();

        mTransparencyHandleSrcRect = new Rect(0, 0, mEditorFrame.getDeleteHandleBitmap().getWidth(),
                mEditorFrame.getDeleteHandleBitmap().getHeight());
        mDeleteHandleSrcRect = new Rect(0, 0, mEditorFrame.getResizeHandleBitmap().getWidth(),
                mEditorFrame.getResizeHandleBitmap().getHeight());
        mResizeAndScaleHandleSrcRect = new Rect(0, 0, mEditorFrame.getRotateHandleBitmap().getWidth(),
                mEditorFrame.getRotateHandleBitmap().getHeight());
        mFrontHandleSrcRect = new Rect(0, 0, mEditorFrame.getFrontHandleBitmap().getWidth(),
                mEditorFrame.getFrontHandleBitmap().getHeight());

        int handleHalfSize = mEditorFrame.getDeleteHandleBitmap().getWidth() / 2;

        mDeleteHandleDstRect = new RectF(0, 0, handleHalfSize << 1, handleHalfSize << 1);
        mResizeAndScaleHandleDstRect = new RectF(0, 0, handleHalfSize << 1, handleHalfSize << 1);
        mFrontHandleDstRect = new RectF(0, 0, handleHalfSize << 1, handleHalfSize << 1);
        mTransparencyHandleDstRect = new RectF(0, 0, handleHalfSize << 1, handleHalfSize << 1);
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

    public void draw(@NonNull Canvas canvas) {

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

        if (mIsDrawHelperFrame) {
            drawHelperFrame(canvas);
        }
    }

    private void drawHelperFrame(Canvas canvas) {
        int offsetValue = ((int) mDeleteHandleDstRect.width()) >> 1;

        mDeleteHandleDstRect.offsetTo(mFrameRect.left - offsetValue, mFrameRect.top - offsetValue);
        mResizeAndScaleHandleDstRect.offsetTo(mFrameRect.right - offsetValue, mFrameRect.bottom - offsetValue);
        mTransparencyHandleDstRect.offsetTo(mFrameRect.right - offsetValue, mFrameRect.top - offsetValue);
        mFrontHandleDstRect.offsetTo(mFrameRect.left - offsetValue, mFrameRect.bottom - offsetValue);

        RectUtil.rotateRect(mDeleteHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateAngle);

        RectUtil.rotateRect(mTransparencyHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateAngle);

        RectUtil.rotateRect(mResizeAndScaleHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateAngle);

        RectUtil.rotateRect(mFrontHandleDstRect, mFrameRect.centerX(),
                mFrameRect.centerY(), mRotateAngle);

        canvas.save();
        canvas.rotate(mRotateAngle, mFrameRect.centerX(), mFrameRect.centerY());
        canvas.drawRect(mFrameRect, mHelperFramePaint);
        canvas.restore();

        canvas.drawBitmap(mEditorFrame.getDeleteHandleBitmap(),
                mDeleteHandleSrcRect, mDeleteHandleDstRect, null);
        canvas.drawBitmap(mEditorFrame.getRotateHandleBitmap(),
                mTransparencyHandleSrcRect, mTransparencyHandleDstRect, null);
        canvas.drawBitmap(mEditorFrame.getResizeHandleBitmap(),
                mResizeAndScaleHandleSrcRect, mResizeAndScaleHandleDstRect, null);
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

    public RectF getRotateAndScaleHandleDstRect() {
        return mResizeAndScaleHandleDstRect;
    }

    public RectF getResizeHandleDstRect() {
        return mResizeAndScaleHandleDstRect;
    }

    public void setHelperFrameOpacity() {
        mHelperFramePaint.setAlpha(255);
    }

    public void resetHelperFrameOpacity() {
        mHelperFramePaint.set(mEditorFrame.getFramePaint());
    }

    public void setIsDrawHelperFrame(boolean isDrawHelperFrame) {
        mIsDrawHelperFrame = isDrawHelperFrame;
    }

    public float getRotateDegree() {
        return mRotateAngle;
    }

    public void updateRotateAndScale(float distanceX, float distanceY) {
        float frameCenterX = mFrameRect.centerX();
        float frameCenterY = mFrameRect.centerY();

        float handleCenterX = mResizeAndScaleHandleDstRect.centerX();
        float handleCenterY = mResizeAndScaleHandleDstRect.centerY();

        float newX = handleCenterX + distanceX;
        float newY = handleCenterY + distanceY;

        float xa = handleCenterX - frameCenterX;
        float ya = handleCenterY - frameCenterY;

        float xb = newX - frameCenterX;
        float yb = newY - frameCenterY;

        float sourceLength = (float) Math.sqrt(Math.pow(xa, 2) + Math.pow(ya, 2));
        float currentLength = (float) Math.sqrt(Math.pow(xb, 2) + Math.pow(yb, 2));

        float scale = currentLength / sourceLength;

        mScale *= scale;

        float newWidth = mFrameRect.width() * mScale;

        if (newWidth < 70) {
            mScale /= scale;
            return;
        }

        double cos = (xa * xb + ya * yb) / (sourceLength * currentLength);

        if (cos > 1 || cos < -1) return;

        float angle = (float) Math.toDegrees(Math.acos(cos));
        float calMatrix = xa * yb - xb * ya;

        int flag = calMatrix > 0 ? 1 : -1;
        angle = flag * angle;

        mRotateAngle += angle;
    }

    public boolean isInside(MotionEvent event) {
        return mFrameRect.contains(event.getX(), event.getY());
    }

    public boolean isInDeleteHandleButton(MotionEvent event) {
        return mDeleteHandleDstRect.contains(event.getX(), event.getY());
    }

    public boolean isInFrontHandleButton(MotionEvent event) {
        return mFrontHandleDstRect.contains(event.getX(), event.getY());
    }

    // TODO: Stickers transparency.
    public boolean isInTransparencyHandleButton(MotionEvent event) {
        return mTransparencyHandleDstRect.contains(event.getX(), event.getY());
    }

    public boolean isInResizeAndScaleHandleButton(MotionEvent event) {
        return mResizeAndScaleHandleDstRect.contains(event.getX(), event.getY());
    }
}
