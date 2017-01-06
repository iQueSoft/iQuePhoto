package net.iquesoft.iquephoto.core.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.core.editor.model.Drawing;
import net.iquesoft.iquephoto.core.editor.model.EditorSticker;
import net.iquesoft.iquephoto.core.editor.model.EditorText;
import net.iquesoft.iquephoto.models.Sticker;
import net.iquesoft.iquephoto.models.Text;
import net.iquesoft.iquephoto.util.LogHelper;
import net.iquesoft.iquephoto.util.MatrixUtil;

import java.util.ArrayList;
import java.util.List;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.NONE;

public class NewImageEditorView extends View {
    private float mLastX;
    private float mLastY;

    private Bitmap mImageBitmap;
    private Bitmap mSupportBitmap;

    private EditorTool mCurrentTool = NONE;

    private Matrix mImageMatrix = new Matrix();
    private Matrix mSupportMatrix = new Matrix();

    private RectF mSrcRect = new RectF();
    private RectF mDstRect = new RectF();

    private Path mDrawingPath = new Path();

    private Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mFilterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mAdjustPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDrawingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDebugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private List<Drawing> mDrawings = new ArrayList<>();
    private List<EditorText> mTexts = new ArrayList<>();
    private List<EditorSticker> mStickers = new ArrayList<>();

    private UndoListener mUndoListener;

    public NewImageEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializePaintsStyle();
        initDrawingPaint();
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

        mImageMatrix.reset();
        mImageMatrix.setRectToRect(mSrcRect, mDstRect, Matrix.ScaleToFit.CENTER);
        mImageMatrix.mapRect(mSrcRect);

        LogHelper.logRect("mSrcRect", mSrcRect);
        LogHelper.logMatrix("mImageMatrix", mImageMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.clipRect(mSrcRect);

        canvas.drawBitmap(mImageBitmap, mImageMatrix, mBitmapPaint);

        switch (mCurrentTool) {
            case NONE:

                break;
            case FILTERS:
                canvas.drawBitmap(mImageBitmap, mImageMatrix, mFilterPaint);
                break;
            case OVERLAY:
                canvas.drawBitmap(mSupportBitmap, mSupportMatrix, mOverlayPaint);
                break;
            case FRAMES:
                canvas.drawBitmap(mSupportBitmap, mSupportMatrix, mBitmapPaint);
                break;
            case DRAWING:
                drawing(canvas);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                actionDown(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                actionPointerDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                actionUp(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }

        return true; //TODO: Check this! super.onTouchEvent(event);
    }

    public void setImageBitmap(@NonNull Bitmap bitmap) {
        mImageBitmap = bitmap;

        mSrcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public void changeTool(EditorTool tool) {
        mCurrentTool = tool;

        invalidate();

        Log.i("ImageEditor", "Tool changed: " + mCurrentTool.name());
    }

    public void applyChanges() {

    }

    public void setUndoListener(UndoListener undoListener) {
        mUndoListener = undoListener;
    }

    public void addText(Text text) {
        //mTexts.add();

        invalidate();
    }

    public void addSticker(Sticker sticker) {
        //mStickers.add();

        invalidate();
    }

    public void setFilter(ColorMatrix colorMatrix) {
        mFilterPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        invalidate();
    }

    public void setFrame(@NonNull Bitmap bitmap) {
        mSupportBitmap = bitmap;

        setupSupportMatrix(mSupportBitmap);

        invalidate();
    }

    public void setOverlay(@NonNull Bitmap bitmap) {
        mSupportBitmap = bitmap;

        setupSupportMatrix(mSupportBitmap);

        invalidate();
    }

    public void setFilterIntensity(int value) {
        mFilterPaint.setAlpha(value);

        invalidate();
    }

    public void setOverlayIntensity(int value) {
        mOverlayPaint.setAlpha(value);

        invalidate();
    }

    public void setBrushColor(@ColorInt int color) {
        mDrawingPaint.setColor(color);
    }

    public void setBrushSize(float size) {
        mDrawingPaint.setStrokeWidth(size);
    }

    private void initializePaintsStyle() {
        mDebugPaint.setStyle(Paint.Style.STROKE);
        mDebugPaint.setColor(Color.RED);
        mDebugPaint.setStrokeWidth(5);

        mOverlayPaint.setAlpha(125);
    }

    private void initDrawingPaint() {
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setColor(Drawing.DEFAULT_COLOR);
        mDrawingPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawingPaint.setStrokeCap(Paint.Cap.ROUND);
        mDrawingPaint.setStrokeWidth(Drawing.DEFAULT_STROKE_WIDTH);
    }

    private void drawTexts(Canvas canvas) {
        for (EditorText text : mTexts) {
            text.draw(canvas);
        }
    }

    private void drawStickers(Canvas canvas) {
        for (EditorSticker sticker : mStickers) {
            sticker.draw(canvas);
        }
    }

    private void actionDown(MotionEvent event) {
        switch (mCurrentTool) {
            case DRAWING:
                brushDown(event);
                break;
        }
    }

    private void actionPointerDown(MotionEvent event) {
        switch (mCurrentTool) {
            case DRAWING:

                break;
        }
    }

    private void actionMove(MotionEvent event) {
        switch (mCurrentTool) {
            case DRAWING:
                brushMove(event);
                break;
        }
    }

    private void actionUp(MotionEvent event) {
        switch (mCurrentTool) {
            case DRAWING:
                brushUp();
                break;
        }
    }

    private void setupSupportMatrix(@NonNull Bitmap bitmap) {
        float sX = mSrcRect.width() / bitmap.getWidth();
        float sY = mSrcRect.height() / bitmap.getHeight();

        mSupportMatrix.reset();

        LogHelper.logMatrix("mSupportMatrix - before", mSupportMatrix);

        mSupportMatrix.postScale(sX, sY);
        mSupportMatrix.postTranslate(mSrcRect.left, mSrcRect.top);

        MatrixUtil.matrixInfo("mSupportMatrix - after", mSupportMatrix);
    }

    private void findCheckedText(MotionEvent event) {

    }

    private void findCheckedSticker(MotionEvent event) {
        // for ()
    }

    // TODO: Not invalidate all.
    private void brushDown(MotionEvent event) {
        Log.i("Drawing", "Brush down");
        mLastX = event.getX();
        mLastY = event.getY();

        mDrawingPath.reset();

        mDrawingPath.reset();
        mDrawingPath.moveTo(mLastX, mLastY);

        invalidate();
    }

    private void brushMove(MotionEvent event) {
        Log.i("Drawing", "Brush move");

        float dX = event.getX() + mLastX;
        float dY = event.getY() + mLastY;

        mDrawingPath.quadTo(mLastX, mLastY, dX / 2, dY / 2);

        mLastX = event.getX();
        mLastY = event.getY();

        invalidate();
    }
    
    private void brushUp() {
        Log.i("Drawing", "Brush up");

        mDrawingPath.lineTo(mLastX, mLastY);
        mDrawings.add(new Drawing(new Paint(mDrawingPaint), mDrawingPath, null));

        mDrawingPath.reset();

        invalidate();
    }

    private void drawing(Canvas canvas) {
        if (mDrawings.size() > 0) {
            for (Drawing drawing : mDrawings) {
                canvas.drawPath(drawing.getPath(), drawing.getPaint());
            }
        }
        if (!mDrawingPath.isEmpty())
            canvas.drawPath(mDrawingPath, mDrawingPaint);
    }
}
