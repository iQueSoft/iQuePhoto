package net.iquesoft.iquephoto.core;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import net.iquesoft.iquephoto.model.Sticker;

public class EditorSticker {
    private Sticker mSticker;
    private Matrix mMatrix;

    private Rect mResizeHandleRect;
    private Rect mDeleteHandleRect;
    private Rect mFrontHandleRect;

    public EditorSticker(Sticker sticker) {
        mSticker = sticker;

        mMatrix = new Matrix();

        mResizeHandleRect = new Rect();
        mDeleteHandleRect = new Rect();
        mFrontHandleRect = new Rect();
    }

    public Bitmap getBitmap() {
        return mSticker.getBitmap();
    }

    public Matrix getMatrix() {
        return mMatrix;
    }

    public void drawSticker(Canvas canvas, Bitmap deleteHandleBitmap, Bitmap resizeHandleBitmap,
                            Bitmap frontHandleBitmap, Paint paint) {
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

        mDeleteHandleRect.left = (int) (f3 - deleteHandleBitmap.getWidth() / 2);
        mDeleteHandleRect.right = (int) (f3 + deleteHandleBitmap.getWidth() / 2);
        mDeleteHandleRect.top = (int) (f4 - deleteHandleBitmap.getHeight() / 2);
        mDeleteHandleRect.bottom = (int) (f4 + deleteHandleBitmap.getHeight() / 2);

        mResizeHandleRect.left = (int) (f7 - resizeHandleBitmap.getWidth() / 2);
        mResizeHandleRect.right = (int) (f7 + resizeHandleBitmap.getWidth() / 2);
        mResizeHandleRect.top = (int) (f8 - resizeHandleBitmap.getHeight() / 2);
        mResizeHandleRect.bottom = (int) (f8 + resizeHandleBitmap.getHeight() / 2);

        mFrontHandleRect.left = (int) (f1 - frontHandleBitmap.getWidth() / 2);
        mFrontHandleRect.right = (int) (f1 + frontHandleBitmap.getWidth() / 2);
        mFrontHandleRect.top = (int) (f2 - frontHandleBitmap.getHeight() / 2);
        mFrontHandleRect.bottom = (int) (f2 + frontHandleBitmap.getHeight() / 2);

            /*dst_flipV.left = (int) (f5 - topBitmapWidth / 2);
            dst_flipV.right = (int) (f5 + topBitmapWidth / 2);
            dst_flipV.top = (int) (f6 - topBitmapHeight / 2);
            dst_flipV.bottom = (int) (f6 + topBitmapHeight / 2);*/

        canvas.drawLine(f1, f2, f3, f4, paint);
        canvas.drawLine(f3, f4, f7, f8, paint);
        canvas.drawLine(f5, f6, f7, f8, paint);
        canvas.drawLine(f5, f6, f1, f2, paint);

        canvas.drawBitmap(deleteHandleBitmap, null, mDeleteHandleRect, null);
        canvas.drawBitmap(resizeHandleBitmap, null, mResizeHandleRect, null);
        canvas.drawBitmap(frontHandleBitmap, null, mFrontHandleRect, null);
    }

}
