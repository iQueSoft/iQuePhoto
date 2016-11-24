package net.iquesoft.iquephoto.core.editor.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.util.BitmapUtil;

public class EditorFrame {
    static final int EDITOR_FRAME_PADDING = 25;

    private Paint mPaint;

    private Bitmap mDeleteHandleBitmap;
    private Bitmap mRotateHandleBitmap;
    private Bitmap mResizeHandleBitmap;
    private Bitmap mFrontHandleBitmap;

    public EditorFrame(Context context) {
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
        mDeleteHandleBitmap = BitmapUtil.drawable2Bitmap(context, R.drawable.ic_handle_delete);
        mResizeHandleBitmap = BitmapUtil.drawable2Bitmap(context, R.drawable.ic_handle_resize);
        mFrontHandleBitmap = BitmapUtil.drawable2Bitmap(context, R.drawable.ic_handle_front);
        mRotateHandleBitmap = BitmapUtil.drawable2Bitmap(context, R.drawable.ic_handle_rotate);
    }

    public Paint getPaint() {
        return mPaint;
    }

    public Bitmap getDeleteHandleBitmap() {
        return mDeleteHandleBitmap;
    }

    public Bitmap getRotateHandleBitmap() {
        return mRotateHandleBitmap;
    }

    public Bitmap getResizeHandleBitmap() {
        return mResizeHandleBitmap;
    }

    public Bitmap getFrontHandleBitmap() {
        return mFrontHandleBitmap;
    }
}
