package net.iquesoft.iquephoto.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import net.iquesoft.iquephoto.model.Sticker;

class EditorSticker {
    private float mMinScale = 0.5f;
    private float mMaxScale = 1.2f;

    private double mHalfDiagonalLength;

    private boolean mIsInEdit;

    private float mLength;
    private float mRotateDegree;

    private Sticker mSticker;
    private Matrix mMatrix;

    private PointF mPoint;

    private float mStickerHeight;
    private float mStickerWight;

    private Rect mRotateHandleRect;
    private Rect mResizeHandleRect;
    private Rect mDeleteHandleRect;
    private Rect mFrontHandleRect;

    private EditorFrame mEditorFrame;

    EditorSticker(Sticker sticker, EditorFrame editorFrame) {
        mSticker = sticker;

        mEditorFrame = editorFrame;

        mMatrix = new Matrix();

        mRotateHandleRect = new Rect();
        mResizeHandleRect = new Rect();
        mDeleteHandleRect = new Rect();
        mFrontHandleRect = new Rect();

        mPoint = new PointF();

        mStickerHeight = getBitmap().getHeight();
        mStickerWight = getBitmap().getWidth();

        int width = getBitmap().getWidth();
        int height = getBitmap().getHeight();

        float scale = (mMinScale + mMaxScale) / 2;

        mMatrix.postScale(scale, scale, width / 2, height / 2);

        mHalfDiagonalLength = Math.hypot(getBitmap().getWidth(), getBitmap().getHeight());
    }


    public Bitmap getBitmap() {
        return mSticker.getBitmap();
    }

    Matrix getMatrix() {
        return mMatrix;
    }

    void drawSticker(Canvas canvas) {
        float[] arrayOfFloat = new float[9];
        mMatrix.getValues(arrayOfFloat);

        float f1 = 0.0F * arrayOfFloat[0] + 0.0F * arrayOfFloat[1] + arrayOfFloat[2];
        float f2 = 0.0F * arrayOfFloat[3] + 0.0F * arrayOfFloat[4] + arrayOfFloat[5];
        float f3 = arrayOfFloat[0] * getBitmap().getWidth() + 0.0F * arrayOfFloat[1] + arrayOfFloat[2];
        float f4 = arrayOfFloat[3] * getBitmap().getWidth() + 0.0F * arrayOfFloat[4] + arrayOfFloat[5];
        float f5 = 0.0F * arrayOfFloat[0] + arrayOfFloat[1] * getBitmap().getHeight() + arrayOfFloat[2];
        float f6 = 0.0F * arrayOfFloat[3] + arrayOfFloat[4] * getBitmap().getHeight() + arrayOfFloat[5];
        float f7 = arrayOfFloat[0] * getBitmap().getWidth() + arrayOfFloat[1] * getBitmap().getHeight() + arrayOfFloat[2];
        float f8 = arrayOfFloat[3] * getBitmap().getWidth() + arrayOfFloat[4] * getBitmap().getHeight() + arrayOfFloat[5];

        canvas.drawBitmap(getBitmap(), mMatrix, null);

        mDeleteHandleRect.left = (int) (f1 - mEditorFrame.getDeleteHandleBitmap().getWidth() / 2);
        mDeleteHandleRect.right = (int) (f1 + mEditorFrame.getDeleteHandleBitmap().getWidth() / 2);
        mDeleteHandleRect.top = (int) (f2 - mEditorFrame.getDeleteHandleBitmap().getHeight() / 2);
        mDeleteHandleRect.bottom = (int) (f2 + mEditorFrame.getDeleteHandleBitmap().getHeight() / 2);

        mRotateHandleRect.left = (int) (f3 - mEditorFrame.getRotateHandleBitmap().getWidth() / 2);
        mRotateHandleRect.right = (int) (f3 + mEditorFrame.getRotateHandleBitmap().getWidth() / 2);
        mRotateHandleRect.top = (int) (f4 - mEditorFrame.getRotateHandleBitmap().getHeight() / 2);
        mRotateHandleRect.bottom = (int) (f4 + mEditorFrame.getRotateHandleBitmap().getHeight() / 2);

        mFrontHandleRect.left = (int) (f5 - mEditorFrame.getFrontHandleBitmap().getWidth() / 2);
        mFrontHandleRect.right = (int) (f5 + mEditorFrame.getFrontHandleBitmap().getWidth() / 2);
        mFrontHandleRect.top = (int) (f6 - mEditorFrame.getFrontHandleBitmap().getHeight() / 2);
        mFrontHandleRect.bottom = (int) (f6 + mEditorFrame.getFrontHandleBitmap().getHeight() / 2);

        mResizeHandleRect.left = (int) (f7 - mEditorFrame.getResizeHandleBitmap().getWidth() / 2);
        mResizeHandleRect.right = (int) (f7 + mEditorFrame.getResizeHandleBitmap().getWidth() / 2);
        mResizeHandleRect.top = (int) (f8 - mEditorFrame.getResizeHandleBitmap().getHeight() / 2);
        mResizeHandleRect.bottom = (int) (f8 + mEditorFrame.getResizeHandleBitmap().getHeight() / 2);

        if (mIsInEdit) {
            canvas.drawLine(f1, f2, f3, f4, mEditorFrame.getPaint());
            canvas.drawLine(f3, f4, f7, f8, mEditorFrame.getPaint());
            canvas.drawLine(f5, f6, f7, f8, mEditorFrame.getPaint());
            canvas.drawLine(f5, f6, f1, f2, mEditorFrame.getPaint());

            canvas.drawBitmap(mEditorFrame.getDeleteHandleBitmap(), null, mDeleteHandleRect, null);
            canvas.drawBitmap(mEditorFrame.getResizeHandleBitmap(), null, mResizeHandleRect, null);
            canvas.drawBitmap(mEditorFrame.getFrontHandleBitmap(), null, mFrontHandleRect, null);
            canvas.drawBitmap(mEditorFrame.getRotateHandleBitmap(), null, mRotateHandleRect, null);
        }
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

    void setLength(float length) {
        mLength = length;
    }

    void setRotateDegree(float rotateDegree) {
        mRotateDegree = rotateDegree;
    }

    float getRotateDegree() {
        return mRotateDegree;
    }

    double getHalfDiagonalLength() {
        return mHalfDiagonalLength;
    }

    float getLength() {
        return mLength;
    }

    PointF getPoint() {
        return mPoint;
    }

    public float getMinScale() {
        return mMinScale;
    }

    public float getMaxScale() {
        return mMaxScale;
    }

    boolean isInEdit() {
        return mIsInEdit;
    }

    void setInEdit(boolean inEdit) {
        mIsInEdit = inEdit;
    }

    float getStickerHeight() {
        return mStickerHeight;
    }

    float getStickerWight() {
        return mStickerWight;
    }
}
