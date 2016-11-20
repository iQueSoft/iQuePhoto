package net.iquesoft.iquephoto.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import net.iquesoft.iquephoto.util.RectUtil;

// TODO: Stickers like as EditorText.
// TODO: Make front handle button for stickers.
public class EditorStickerItem {
    private static final float MIN_SCALE = 0.15f;
    private static final int HELP_BOX_PAD = 25;

    private static final int BUTTON_WIDTH = 30;

    public Bitmap mBitmap;

    private RectF mImageRect;

    private Rect mSrcRect;
    private RectF mDstRect;

    private RectF mFrameRect;
    private Rect mFrameHandlesRect;

    private RectF mDeleteHandleRect;
    private RectF mResizeHandleRect;

    private RectF mRotateHandleDstRect;
    private RectF mDeleteHandleDstRect;

    private Matrix mMatrix;
    private float mRotateAngle = 0;
    boolean isDrawHelpTool = false;

    private float initWidth;

    private EditorFrame mEditorFrame;

    public EditorStickerItem(Bitmap bitmap, RectF imageRect, EditorFrame editorFrame) {
        mBitmap = bitmap;

        mImageRect = imageRect;
        mEditorFrame = editorFrame;

        initialize();
    }

    public void initialize() {
        mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());

        int stickerWidth = Math.min(mBitmap.getWidth(), (int) mImageRect.width() >> 1);
        int stickerHeight = (int) stickerWidth * mBitmap.getHeight() / mBitmap.getWidth();

        int left = ((int) mImageRect.width() >> 1) - (stickerWidth >> 1);
        int top = ((int) mImageRect.height() >> 1) - (stickerHeight >> 1);

        mDstRect = new RectF(left, top, left + stickerWidth, top + stickerHeight);

        mMatrix = new Matrix();
        mMatrix.postTranslate(mDstRect.left, mDstRect.top);
        mMatrix.postScale((float) stickerWidth / mBitmap.getWidth(),
                (float) stickerHeight / mBitmap.getHeight(), mDstRect.left,
                mDstRect.top);

        initWidth = mDstRect.width();

        isDrawHelpTool = true;

        mFrameRect = new RectF(mDstRect);
        updateFrameRect();

        mFrameHandlesRect = new Rect(0, 0, mEditorFrame.getDeleteHandleBitmap().getWidth(),
                mEditorFrame.getDeleteHandleBitmap().getHeight());

        mDeleteHandleRect = new RectF(mFrameRect.left - BUTTON_WIDTH, mFrameRect.top
                - BUTTON_WIDTH, mFrameRect.left + BUTTON_WIDTH, mFrameRect.top
                + BUTTON_WIDTH);
        mResizeHandleRect = new RectF(mFrameRect.right - BUTTON_WIDTH, mFrameRect.bottom
                - BUTTON_WIDTH, mFrameRect.right + BUTTON_WIDTH, mFrameRect.bottom
                + BUTTON_WIDTH);

        mRotateHandleDstRect = new RectF(mResizeHandleRect);
        mDeleteHandleDstRect = new RectF(mDeleteHandleRect);
    }

    private void updateFrameRect() {
        mFrameRect.left -= HELP_BOX_PAD;
        mFrameRect.right += HELP_BOX_PAD;
        mFrameRect.top -= HELP_BOX_PAD;
        mFrameRect.bottom += HELP_BOX_PAD;
    }

    public void actionMove(float dx, float dy) {
        mMatrix.postTranslate(dx, dy);

        mDstRect.offset(dx, dy);

        mFrameRect.offset(dx, dy);
        mDeleteHandleRect.offset(dx, dy);
        mResizeHandleRect.offset(dx, dy);

        mRotateHandleDstRect.offset(dx, dy);
        mDeleteHandleDstRect.offset(dx, dy);
    }

    public void updateRotateAndScale(final float oldx, final float oldy,
                                     final float dx, final float dy) {
        float c_x = mDstRect.centerX();
        float c_y = mDstRect.centerY();

        float x = mRotateHandleDstRect.centerX();
        float y = mRotateHandleDstRect.centerY();

        float n_x = x + dx;
        float n_y = y + dy;

        float xa = x - c_x;
        float ya = y - c_y;

        float xb = n_x - c_x;
        float yb = n_y - c_y;

        float srcLen = (float) Math.sqrt(xa * xa + ya * ya);
        float curLen = (float) Math.sqrt(xb * xb + yb * yb);

        float scale = curLen / srcLen;

        float newWidth = mDstRect.width() * scale;
        if (newWidth / initWidth < MIN_SCALE) {
            return;
        }

        mMatrix.postScale(scale, scale, mDstRect.centerX(),
                mDstRect.centerY());

        RectUtil.scaleRect(mDstRect, scale);

        mFrameRect.set(mDstRect);
        updateFrameRect();
        mResizeHandleRect.offsetTo(mFrameRect.right - BUTTON_WIDTH, mFrameRect.bottom
                - BUTTON_WIDTH);
        mDeleteHandleRect.offsetTo(mFrameRect.left - BUTTON_WIDTH, mFrameRect.top
                - BUTTON_WIDTH);

        mRotateHandleDstRect.offsetTo(mFrameRect.right - BUTTON_WIDTH, mFrameRect.bottom
                - BUTTON_WIDTH);
        mDeleteHandleDstRect.offsetTo(mFrameRect.left - BUTTON_WIDTH, mFrameRect.top
                - BUTTON_WIDTH);

        double cos = (xa * xb + ya * yb) / (srcLen * curLen);
        if (cos > 1 || cos < -1)
            return;
        float angle = (float) Math.toDegrees(Math.acos(cos));

        float calMatrix = xa * yb - xb * ya;

        int flag = calMatrix > 0 ? 1 : -1;
        angle = flag * angle;

        mRotateAngle += angle;
        mMatrix.postRotate(angle, mDstRect.centerX(),
                mDstRect.centerY());

        RectUtil.rotateRect(mRotateHandleDstRect, mDstRect.centerX(),
                mDstRect.centerY(), mRotateAngle);
        RectUtil.rotateRect(mDeleteHandleDstRect, mDstRect.centerX(),
                mDstRect.centerY(), mRotateAngle);

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mMatrix, null);

        if (isDrawHelpTool) {
            canvas.save();
            canvas.rotate(mRotateAngle, mFrameRect.centerX(), mFrameRect.centerY());
            canvas.drawRect(mFrameRect, mEditorFrame.getPaint());

            canvas.drawBitmap(mEditorFrame.getDeleteHandleBitmap(), mFrameHandlesRect, mDeleteHandleRect, null);
            canvas.drawBitmap(mEditorFrame.getResizeHandleBitmap(), mFrameHandlesRect, mResizeHandleRect, null);
            canvas.restore();
        }
    }
}
