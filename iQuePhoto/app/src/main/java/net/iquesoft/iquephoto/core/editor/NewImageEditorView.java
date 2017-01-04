package net.iquesoft.iquephoto.core.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.util.LogHelper;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.NONE;

public class NewImageEditorView extends View {
    private Bitmap mBitmap;

    private EditorCommand mCurrentCommand = NONE;

    private Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDebugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Matrix mMatrix = new Matrix();
    private Matrix mSupportMatrix = new Matrix();

    private RectF mSrcRect = new RectF();
    private RectF mDstRect = new RectF();

    public NewImageEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializePaintsStyle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mDstRect.set(0, 0, getWidth(), getHeight());

        LogHelper.logRect("mDstRect", mDstRect);

        mMatrix.reset();
        mMatrix.setRectToRect(mSrcRect, mDstRect, Matrix.ScaleToFit.CENTER);
        mMatrix.mapRect(mSrcRect);

        LogHelper.logRect("mSrcRect", mSrcRect);
        LogHelper.logMatrix("mMatrix", mMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, 250, 250, mDebugPaint);

        canvas.clipRect(mSrcRect);

        canvas.drawBitmap(mBitmap, mMatrix, mBitmapPaint);
    }

    public void setImageBitmap(@NonNull Bitmap bitmap) {
        mBitmap = bitmap;

        mSrcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    private void initializePaintsStyle() {
        mDebugPaint.setStyle(Paint.Style.STROKE);
        mDebugPaint.setColor(Color.RED);
        mDebugPaint.setStrokeWidth(5);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("onTouch()", "X = " + event.getX() + "\n" + "Y = " + event.getY());

        return super.onTouchEvent(event);
    }
}
