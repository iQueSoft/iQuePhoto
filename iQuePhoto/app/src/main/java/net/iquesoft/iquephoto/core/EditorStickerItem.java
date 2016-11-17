package net.iquesoft.iquephoto.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.MotionEvent;

import net.iquesoft.iquephoto.utils.RectUtil;

// TODO: Stickers like as EditorText.
// TODO: Make front handle button for stickers.
public class EditorStickerItem {
    private Bitmap mBitmap;

    private int mX;
    private int mY;

    private float mRotateAngle = 0;

    private boolean mIsInEdit;

    private Rect mStickerSrcRect;
    private RectF mStickerDstRect;

    private Rect mRotateHandleSrcRect;
    private Rect mResizeHandleSrcRect;
    private Rect mDeleteHandleSrcRect;
    private Rect mFrontHandleSrcRect;

    private Rect mFrameRect;
    private RectF mDeleteHandleDstRect;
    private RectF mRotateHandleDstRect;
    private RectF mResizeHandleDstRect;
    private RectF mFrontHandleDstRect;

    private Matrix mMatrix;

    private EditorFrame mEditorFrame;

    EditorStickerItem(Bitmap bitmap, EditorFrame editorFrame) {
        mBitmap = bitmap;
        mEditorFrame = editorFrame;

        initialize();
    }

    private void initialize() {
        mStickerSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mStickerDstRect = new RectF();
        mFrameRect = new Rect();

        mMatrix = new Matrix();

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

    void draw(Canvas canvas) {
        //mMatrix.postTranslate(mStickerDstRect)

        canvas.drawBitmap(mBitmap, mMatrix, null);

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

        // TODO: Doesn't draw sticker frame.
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

    void actionMove(MotionEvent event) {
        float dx = 0;
        float dy = 0;

        mMatrix.postTranslate(dx, dy);

        mStickerDstRect.offset(dx, dy);

        mDeleteHandleDstRect.offset(dx, dy);

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

    Rect getFrameRect() {
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
        mRotateAngle = rotateDegree;
    }

    float getRotateDegree() {
        return mRotateAngle;
    }

    boolean isInEdit() {
        return mIsInEdit;
    }
}
