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
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import net.iquesoft.iquephoto.core.editor.enums.EditorMode;
import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.core.editor.model.Drawing;
import net.iquesoft.iquephoto.core.editor.model.EditorFrame;
import net.iquesoft.iquephoto.core.editor.model.EditorImage;
import net.iquesoft.iquephoto.core.editor.model.EditorSticker;
import net.iquesoft.iquephoto.core.editor.model.EditorText;
import net.iquesoft.iquephoto.core.editor.model.EditorTiltShiftRadial;
import net.iquesoft.iquephoto.core.editor.model.EditorVignette;
import net.iquesoft.iquephoto.models.Text;
import net.iquesoft.iquephoto.util.BitmapUtil;
import net.iquesoft.iquephoto.util.LogHelper;
import net.iquesoft.iquephoto.util.MatrixUtil;

import java.util.ArrayList;
import java.util.List;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.NONE;

// TODO: CustomViews as MvpView {@link https://github.com/Arello-Mobile/Moxy/wiki/CustomView-as-MvpView}
public class NewImageEditorView extends View {
    private static final String TAG = "Image Editor";

    private float mLastX;
    private float mLastY;

    private int mStraightenTransformValue = 0;

    private boolean mIsOriginalImageDisplayed;

    private Bitmap mImageBitmap;
    private Bitmap mSupportBitmap;

    private EditorTool mCurrentTool = NONE;
    private EditorMode mCurrentMode = EditorMode.NONE;

    private Matrix mImageMatrix = new Matrix();
    private Matrix mSupportMatrix = new Matrix();
    private Matrix mTransformMatrix = new Matrix();

    private RectF mSrcRect = new RectF();
    private RectF mDstRect = new RectF();

    private Path mDrawingPath = new Path();

    private Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mFilterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mAdjustPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDrawingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDebugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private EditorSticker mCurrentCheckedSticker;

    private EditorFrame mEditorFrame;
    private EditorVignette mVignette;
    private EditorTiltShiftRadial mRadialTiltShift;

    private List<Drawing> mDrawings = new ArrayList<>();
    private List<EditorText> mTexts = new ArrayList<>();
    private List<EditorSticker> mStickers = new ArrayList<>();
    private List<EditorImage> mImages = new ArrayList<>();

    private UndoListener mUndoListener;

    public NewImageEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializePaintsStyle();
        initDrawingPaint();

        mEditorFrame = new EditorFrame(context);
        mVignette = new EditorVignette(this);
        mRadialTiltShift = new EditorTiltShiftRadial(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mDstRect.set(0, 0, getWidth(), getHeight());

        LogHelper.logRect("mDstRect", mDstRect);

        mImageMatrix.reset();
        mImageMatrix.setRectToRect(mSrcRect, mDstRect, Matrix.ScaleToFit.CENTER);
        mImageMatrix.mapRect(mSrcRect);

        mTransformMatrix.set(mImageMatrix);

        LogHelper.logRect("mSrcRect", mSrcRect);
        LogHelper.logMatrix("mImageMatrix", mImageMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap;

        if (!mIsOriginalImageDisplayed) {
            bitmap = getAlteredBitmap();
        } else {
            bitmap = mImageBitmap;
        }

        canvas.clipRect(mSrcRect);

        canvas.drawBitmap(bitmap, mImageMatrix, mBitmapPaint);

        switch (mCurrentTool) {
            case NONE:
                break;
            case FILTERS:
                canvas.drawBitmap(bitmap, mImageMatrix, mFilterPaint);
                break;
            /*case BRIGHTNESS:
                canvas.drawBitmap(bitmap, mImageMatrix, mAdjustPaint);
                break;
            case CONTRAST:
                canvas.drawBitmap(bitmap, mImageMatrix, mAdjustPaint);
                break;*/
            case OVERLAY:
                canvas.drawBitmap(mSupportBitmap, mSupportMatrix, mOverlayPaint);
                break;
            case FRAMES:
                canvas.drawBitmap(mSupportBitmap, mSupportMatrix, mBitmapPaint);
                break;
            case DRAWING:
                drawing(canvas);
                break;
            case STICKERS:
                drawStickers(canvas);
                break;
            case TEXT:
                drawTexts(canvas);
                break;
            case VIGNETTE:
                mVignette.draw(canvas);
                break;
            case TRANSFORM_STRAIGHTEN:
                canvas.drawBitmap(bitmap, mTransformMatrix, mBitmapPaint);
                break;
            case TILT_SHIFT_RADIAL:
                mRadialTiltShift.draw(canvas, bitmap, mImageMatrix, mBitmapPaint);
                break;
            default:
                canvas.drawBitmap(bitmap, mImageMatrix, mAdjustPaint);
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

    public Bitmap getAlteredBitmap() {
        if (!mImages.isEmpty()) {
            return mImages.get(mImages.size() - 1).getBitmap();
        }

        return mImageBitmap;
    }

    public void undo() {
        mImages.remove(mImages.size() - 1);
        mUndoListener.hasChanged(mImages.size());

        invalidate();
    }

    public void changeTool(EditorTool tool) {
        mCurrentTool = tool;

        switch (mCurrentTool) {
            case VIGNETTE:
                mVignette.updateRect(mSrcRect);
                break;
            case TILT_SHIFT_RADIAL:
                mRadialTiltShift.updateRect(mSrcRect);
                return;
        }

        invalidate();

        Log.i("ImageEditor", "Tool changed: " + mCurrentTool.name());
    }

    public void applyChanges() {
        new ImageProcessingTask().execute(mCurrentTool);
    }

    public void setUndoListener(UndoListener undoListener) {
        mUndoListener = undoListener;
    }

    public boolean hasChanged() {
        return mImages.size() != 0;
    }

    public void addText(Text text) {
        EditorText editorText = new EditorText(text, mEditorFrame);
        editorText.setX(mSrcRect.centerX());
        editorText.setY(mSrcRect.centerY());

        mTexts.add(editorText);

        invalidate();
    }

    public void addSticker(Bitmap bitmap) {
        mStickers.add(new EditorSticker(bitmap, mSrcRect, mEditorFrame));

        Log.i(TAG, "Sticker added! (" + String.valueOf(mStickers.size()) + ")");

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

    public void setVignetteIntensity(int value) {
        mVignette.updateMask(value);

        invalidate();
    }

    public void setOverlayIntensity(int value) {
        mOverlayPaint.setAlpha(value);

        invalidate();
    }

    public void setBrightnessValue(int value) {
        if (value != 0) {
            mAdjustPaint.setColorFilter(
                    new ColorMatrixColorFilter(AdjustColorFilter.getBrightnessMatrix(value))
            );

            invalidate();
        }
    }

    public void setContrastValue(int value) {
        if (value != 0) {
            mAdjustPaint.setColorFilter(
                    new ColorMatrixColorFilter(AdjustColorFilter.getContrastMatrix(value))
            );

            invalidate();
        }
    }

    public void setWarmthValue(int value) {
        if (value != 0) {
            mAdjustPaint.setColorFilter(
                    new ColorMatrixColorFilter(AdjustColorFilter.getWarmthMatrix(value))
            );

            invalidate();
        }
    }

    public void setStraightenTransformValue(int value) {
        if (value != 0) {
            mStraightenTransformValue = value;

            mTransformMatrix.set(mImageMatrix);

            float width = mSrcRect.width();
            float height = mSrcRect.height();

            if (width >= height) {
                width = mSrcRect.height();
                height = mSrcRect.width();
            }

            float alpha = (float) Math.atan(height / width);

            float length1 = (width / 2) / (float) Math.cos(alpha - Math.abs(Math.toRadians(value)));

            float length2 = (float) Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));

            float scale = length2 / length1;

            float centerX = mSrcRect.centerX();
            float centerY = mSrcRect.centerY();

            float dX = centerX * (1 - scale);
            float dY = centerY * (1 - scale);

            mTransformMatrix.postScale(scale, scale);
            mTransformMatrix.postTranslate(dX, dY);
            mTransformMatrix.postRotate(value, centerX, centerY);

            invalidate();
        }
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
            case NONE:
                setIsOriginalImageDisplayed(true);
                break;
            case DRAWING:
                brushDown(event);
                break;
            case STICKERS:
                findCheckedSticker(event);
                break;
            case VIGNETTE:
                mVignette.actionDown(event);
                break;
            case TILT_SHIFT_RADIAL:
                mRadialTiltShift.actionDown(event);
                break;
        }
    }

    private void actionPointerDown(MotionEvent event) {
        switch (mCurrentTool) {
            case DRAWING:

                break;
            case VIGNETTE:
                mVignette.actionPointerDown(event);
            case TILT_SHIFT_RADIAL:
                mRadialTiltShift.actionPointerDown(event);
                break;
        }
    }

    private void actionMove(MotionEvent event) {
        switch (mCurrentTool) {
            case DRAWING:
                brushMove(event);
                break;
            case STICKERS:
                if (mCurrentCheckedSticker != null) {
                    moveSticker(event);
                }
                break;
            case VIGNETTE:
                mVignette.actionMove(event);
                break;
            case TILT_SHIFT_RADIAL:
                mRadialTiltShift.actionMove(event);
                break;

        }
    }

    private void actionUp(MotionEvent event) {
        switch (mCurrentTool) {
            case NONE:
                setIsOriginalImageDisplayed(false);
                break;
            case DRAWING:
                brushUp();
                break;
            case STICKERS:
                if (mCurrentCheckedSticker != null) {
                    mCurrentCheckedSticker.resetHelperFrameOpacity();
                }
                break;
            case VIGNETTE:
                mVignette.actionUp();
                break;
            case TILT_SHIFT_RADIAL:
                mRadialTiltShift.actionUp();
                break;
        }

        mCurrentMode = EditorMode.NONE;
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
        for (int i = mStickers.size() - 1; i >= 0; i--) {
            EditorSticker editorSticker = mStickers.get(i);

            if (editorSticker.isInside(event)) {
                mCurrentCheckedSticker = editorSticker;
                mCurrentMode = EditorMode.MOVE;

                mCurrentCheckedSticker.setHelperFrameOpacity();

                mLastX = event.getX();
                mLastY = event.getY();

                return;
            } else if (editorSticker.isInDeleteHandleButton(event)) {
                mCurrentCheckedSticker = null;

                mCurrentMode = EditorMode.NONE;

                mStickers.remove(i);

                invalidate();
                return;
            } else if (editorSticker.isInResizeAndScaleHandleButton(event)) {
                mCurrentCheckedSticker = editorSticker;
                mCurrentMode = EditorMode.ROTATE_AND_SCALE;

                mCurrentCheckedSticker.setHelperFrameOpacity();

                mLastX = event.getX();
                mLastY = event.getY();
                return;
            } else if (editorSticker.isInFrontHandleButton(event)) {
                mCurrentMode = EditorMode.NONE;

                EditorSticker sticker = mStickers.remove(i);
                mStickers.add(sticker);

                invalidate();
                return;
            }
        }

        mCurrentCheckedSticker = null;

        mCurrentMode = EditorMode.NONE;
    }

    private void moveSticker(MotionEvent event) {
        switch (mCurrentMode) {
            case MOVE:
                mCurrentCheckedSticker.actionMove(
                        getDeltaX(event),
                        getDeltaY(event)
                );

                mLastX = event.getX();
                mLastY = event.getY();

                invalidate();
                break;
            case ROTATE_AND_SCALE:
                mCurrentCheckedSticker.updateRotateAndScale(
                        getDeltaX(event),
                        getDeltaY(event)
                );

                mLastX = event.getX();
                mLastY = event.getY();

                invalidate();
                break;
        }
    }

    private float getDeltaX(MotionEvent event) {
        return event.getX() - mLastX;
    }

    private float getDeltaY(MotionEvent event) {
        return event.getY() - mLastY;
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

    private void setIsOriginalImageDisplayed(boolean isOriginalImageDisplayed) {
        mIsOriginalImageDisplayed = isOriginalImageDisplayed;

        invalidate();
    }

    private class ImageProcessingTask extends AsyncTask<EditorTool, Void, Bitmap> {
        private int mImageHeight;
        private int mImageWidth;

        private Bitmap mBitmap;
        private Canvas mCanvas;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mProgressDialog.show();

            mBitmap = getAlteredBitmap().copy(getAlteredBitmap().getConfig(), true);
        }

        @Override
        protected Bitmap doInBackground(EditorTool... editorTools) {
            mCanvas = new Canvas(mBitmap);

            mImageHeight = mBitmap.getHeight();
            mImageWidth = mBitmap.getWidth();

            switch (editorTools[0]) {
                case NONE:
                    break;
                case FILTERS:
                    mCanvas.drawBitmap(mBitmap, 0, 0, mFilterPaint);
                    break;
                case OVERLAY:
                    calculateSupportMatrix(mSupportBitmap);
                    mCanvas.drawBitmap(mSupportBitmap, mSupportMatrix, mOverlayPaint);
                    break;
                case STICKERS:
                    drawStickers(mCanvas);
                    break;
                case FRAMES:
                    calculateSupportMatrix(mSupportBitmap);
                    mCanvas.drawBitmap(mSupportBitmap, mSupportMatrix, mBitmapPaint);
                    break;
                case VIGNETTE:
                    mVignette.prepareToDraw(mCanvas, mImageMatrix);
                    mVignette.draw(mCanvas);
                    break;
                case TRANSFORM_STRAIGHTEN:
                    mCanvas.drawBitmap(mBitmap, mTransformMatrix, mBitmapPaint);
                    break;
                default:
                    mCanvas.drawBitmap(mBitmap, 0, 0, mAdjustPaint);
                    break;
                /*case TEXT:
                    drawTexts(mCanvas);
                    break;
                case DRAWING:
                    break;
                case TILT_SHIFT_RADIAL:
                    break;
                case VIGNETTE:
                    // TODO: Draw vignette on image with original size.
                    mEditorVignette.prepareToDraw(mCanvas, mMatrix);
                    mEditorVignette.draw(mCanvas);
                    break;

                case TRANSFORM_STRAIGHTEN:
                    mCanvas.save(Canvas.CLIP_SAVE_FLAG);
                    mCanvas.setMatrix(getTransformStraightenMatrix(mTransformStraightenValue));
                    mCanvas.drawBitmap(mBitmap, 0, 0,
//                            getTransformStraightenMatrix(mTransformStraightenValue),
                            mImagePaint);
                    mCanvas.restore();
                    break;*/
            }

            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            switch (mCurrentTool) {
                case STICKERS:
                    mStickers.clear();
                    break;
            }

            mImages.add(new EditorImage(mCurrentTool, bitmap));

            mUndoListener.hasChanged(mImages.size());

            invalidate();
            // mProgressDialog.dismiss();
        }

        private void drawStickers(Canvas canvas) {
            for (EditorSticker sticker : mStickers) {
                sticker.prepareToDraw(mImageMatrix);
                sticker.draw(canvas);
            }
        }

       /* private void drawTexts(Canvas canvas) {
            for (EditorText text : mTextsList) {
                text.prepareToDraw(mMatrix);
                text.draw(canvas);
            }
        }*/

        private void calculateSupportMatrix(Bitmap bitmap) {
            float height = bitmap.getHeight();
            float width = bitmap.getWidth();

            float sX = mImageWidth / width;
            float sY = mImageHeight / height;

            BitmapUtil.logBitmapInfo("calcSupportMatrix()", bitmap);

            Log.i("calcSupportMatrix", "sX = " + sX + "\nsY = " + sY);

            mSupportMatrix.reset();

            MatrixUtil.matrixInfo("mSupportMatrix - before", mSupportMatrix);

            mSupportMatrix.postScale(sX, sY);
            mSupportMatrix.postTranslate(0, 0);

            MatrixUtil.matrixInfo("mSupportMatrix - after", mSupportMatrix);
        }
    }
}
