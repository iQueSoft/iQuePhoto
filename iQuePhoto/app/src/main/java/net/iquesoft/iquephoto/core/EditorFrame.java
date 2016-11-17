package net.iquesoft.iquephoto.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import net.iquesoft.iquephoto.R;

class EditorFrame {
    static final int EDITOR_FRAME_PADDING = 25;

    private Paint mPaint;

    private Bitmap mDeleteHandleBitmap;
    private Bitmap mRotateHandleBitmap;
    private Bitmap mResizeHandleBitmap;
    private Bitmap mFrontHandleBitmap;

    EditorFrame(Context context) {
        initFramePaint();
        initHandlesBitmap(context);
    }

    private void initFramePaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5.5f);
    }

    private void initHandlesBitmap(Context context) {
        mDeleteHandleBitmap = ((BitmapDrawable) context.getResources()
                .getDrawable(R.drawable.ic_handle_delete))
                .getBitmap();
        mResizeHandleBitmap = ((BitmapDrawable) context.getResources()
                .getDrawable(R.drawable.ic_handle_resize))
                .getBitmap();
        mFrontHandleBitmap = ((BitmapDrawable) context.getResources()
                .getDrawable(R.drawable.ic_handle_front))
                .getBitmap();
        mRotateHandleBitmap = ((BitmapDrawable) context.getResources()
                .getDrawable(R.drawable.ic_handle_rotate))
                .getBitmap();
    }

    Paint getPaint() {
        return mPaint;
    }

    Bitmap getDeleteHandleBitmap() {
        return mDeleteHandleBitmap;
    }

    Bitmap getRotateHandleBitmap() {
        return mRotateHandleBitmap;
    }

    Bitmap getResizeHandleBitmap() {
        return mResizeHandleBitmap;
    }

    Bitmap getFrontHandleBitmap() {
        return mFrontHandleBitmap;
    }
}
