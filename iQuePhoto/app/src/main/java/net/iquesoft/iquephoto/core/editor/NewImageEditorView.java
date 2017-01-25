package net.iquesoft.iquephoto.core.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.core.editor.model.Drawing;
import net.iquesoft.iquephoto.core.editor.model.EditorFrame;
import net.iquesoft.iquephoto.core.editor.model.EditorSticker;
import net.iquesoft.iquephoto.core.editor.model.EditorText;
import net.iquesoft.iquephoto.core.editor.model.EditorTiltShiftRadial;
import net.iquesoft.iquephoto.core.editor.model.EditorVignette;
import net.iquesoft.iquephoto.models.Text;
import net.iquesoft.iquephoto.ui.dialogs.LoadingDialog;

import java.util.List;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.NONE;

public class NewImageEditorView extends View implements ImageEditorViewView {
    @InjectPresenter
    ImageEditorViewPresenter mPresenter;

    @ProvidePresenter
    ImageEditorViewPresenter provideImageEditorViewPresenter() {
        return new ImageEditorViewPresenter(getContext());
    }
    
    private MvpDelegate mParentDelegate;
    private MvpDelegate<NewImageEditorView> mMvpDelegate;

    private boolean mIsOriginalImageDisplayed;

    private Bitmap mImageBitmap;
    private Bitmap mAlteredImageBitmap;
    private Bitmap mSupportBitmap;

    private EditorTool mCurrentTool = NONE;

    private Matrix mImageMatrix;
    private Matrix mSupportMatrix;
    private Matrix mTransformMatrix = new Matrix();

    private RectF mImageRect;

    private Path mDrawingPath;

    private Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mFilterPaint;
    private Paint mOverlayPaint;
    private Paint mAdjustPaint;
    private Paint mDrawingPaint;

    private EditorFrame mEditorFrame;
    private EditorVignette mVignette;
    private EditorTiltShiftRadial mRadialTiltShift;

    private List<Drawing> mDrawings;
    private List<EditorText> mTexts;
    private List<EditorSticker> mStickers;

    private LoadingDialog mLoadingDialog;

    public NewImageEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrawingPaint();

        mLoadingDialog = new LoadingDialog(context);
        mEditorFrame = new EditorFrame(context);
        mVignette = new EditorVignette(this);

        mRadialTiltShift = new EditorTiltShiftRadial(this);
    }

    public void init(MvpDelegate patentDelegate) {
        mParentDelegate = patentDelegate;

        getMvpDelegate().onCreate();
        getMvpDelegate().onAttach();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mPresenter.setupImage(getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap;

        if (!mIsOriginalImageDisplayed) {
            if (mAlteredImageBitmap != null) {
                bitmap = mAlteredImageBitmap;
            } else {
                bitmap = mImageBitmap;
            }
        } else {
            bitmap = mImageBitmap;
        }

        canvas.clipRect(mImageRect);

        canvas.drawBitmap(bitmap, mImageMatrix, mBitmapPaint);

        switch (mCurrentTool) {
            case NONE:
                break;
            case FILTERS:
                canvas.drawBitmap(bitmap, mImageMatrix, mFilterPaint);
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
        mPresenter.viewTouched(event);

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        getMvpDelegate().onSaveInstanceState();
        getMvpDelegate().onDetach();
    }

    public MvpDelegate<NewImageEditorView> getMvpDelegate() {
        if (mMvpDelegate != null) {
            return mMvpDelegate;
        }

        mMvpDelegate = new MvpDelegate<>(this);
        mMvpDelegate.setParentDelegate(mParentDelegate, String.valueOf(getId()));

        return mMvpDelegate;
    }

    public void setImageBitmap(@NonNull Bitmap bitmap) {
        mPresenter.setImageBitmap(bitmap);
    }

    public Bitmap getAlteredImageBitmap() {
        if (mAlteredImageBitmap != null) {
            return mAlteredImageBitmap;
        }

        return mImageBitmap;
    }

    public boolean hasChanges() {
        return mAlteredImageBitmap != null;
    }

    public void undo() {
        mPresenter.undo();
    }

    public void changeTool(EditorTool tool) {
        mPresenter.changeTool(tool);
    }

    public void applyChanges() {
        mPresenter.applyChanges();
    }

    public void setUndoListener(UndoListener undoListener) {
        mPresenter.setUndoListener(undoListener);
    }

    /*public boolean hasChanged() {
        return mImages.size() != 0;
    }*/

    public void addText(Text text) {
        mPresenter.addText(text);
    }

    public void addSticker(Bitmap bitmap) {
        mPresenter.addSticker(bitmap);
    }

    public void setFilter(ColorMatrix colorMatrix) {
        mPresenter.setFilter(colorMatrix);
    }

    public void setFrame(@NonNull Bitmap bitmap) {
        mPresenter.setFrame(bitmap);
    }

    public void setOverlay(@NonNull Bitmap bitmap) {
        mPresenter.setOverlay(bitmap);
    }

    public void setFilterIntensity(int value) {
        mPresenter.changeFilterIntensity(value);
    }

    public void setVignetteIntensity(int value) {
        mVignette.updateMask(value);

        invalidate();
    }

    public void setOverlayIntensity(int value) {
        mPresenter.changeOverlayIntensity(value);
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
            //mStraightenTransformValue = value;

            mTransformMatrix.set(mImageMatrix);

            float width = mImageRect.width();
            float height = mImageRect.height();

            if (width >= height) {
                width = mImageRect.height();
                height = mImageRect.width();
            }

            float alpha = (float) Math.atan(height / width);

            float length1 = (width / 2) / (float) Math.cos(alpha - Math.abs(Math.toRadians(value)));

            float length2 = (float) Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));

            float scale = length2 / length1;

            float centerX = mImageRect.centerX();
            float centerY = mImageRect.centerY();

            float dX = centerX * (1 - scale);
            float dY = centerY * (1 - scale);

            mTransformMatrix.postScale(scale, scale);
            mTransformMatrix.postTranslate(dX, dY);
            mTransformMatrix.postRotate(value, centerX, centerY);

            invalidate();
        }
    }

    public void setBrushColor(@ColorInt int color) {
        mPresenter.changeBrushColor(color);
    }

    public void setBrushSize(float size) {
        mPresenter.changeBrushSize(size);
    }

    private void initDrawingPaint() {
        mDrawingPaint = new Paint();
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
        if (mStickers != null) {
            for (EditorSticker sticker : mStickers) {
                sticker.draw(canvas);
            }
        }
    }

    private void actionDown(MotionEvent event) {
        switch (mCurrentTool) {
            case NONE:
                setIsOriginalImageDisplayed(true);
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
            case VIGNETTE:
                mVignette.actionPointerDown(event);
            case TILT_SHIFT_RADIAL:
                mRadialTiltShift.actionPointerDown(event);
                break;
        }
    }

    private void actionMove(MotionEvent event) {
        switch (mCurrentTool) {

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

            case VIGNETTE:
                mVignette.actionUp();
                break;
            case TILT_SHIFT_RADIAL:
                mRadialTiltShift.actionUp();
                break;
        }


    }

    private void drawing(Canvas canvas) {
        if (mDrawings != null) {
            if (!mDrawings.isEmpty()) {
                for (Drawing drawing : mDrawings) {
                    canvas.drawPath(drawing.getPath(), drawing.getPaint());
                }
            }
        }
        if (mDrawingPath != null) {
            if (!mDrawingPath.isEmpty()) {
                canvas.drawPath(mDrawingPath, mDrawingPaint);
            }
        }
    }

    private void setIsOriginalImageDisplayed(boolean isOriginalImageDisplayed) {
        mIsOriginalImageDisplayed = isOriginalImageDisplayed;

        invalidate();
    }

    @Override
    public void setupImage(Bitmap bitmap, Matrix imageMatrix, RectF imageRect) {
        mImageBitmap = bitmap;
        mImageMatrix = imageMatrix;
        mImageRect = imageRect;

        invalidate();
    }

    @Override
    public void showOriginalImage(boolean display) {
        mIsOriginalImageDisplayed = display;

        invalidate();
    }

    @Override
    public void toolChanged(EditorTool tool) {
        mCurrentTool = tool;

        invalidate();
    }

    @Override
    public void overlayChanged(Bitmap bitmap, Matrix matrix, Paint paint) {
        mSupportBitmap = bitmap;
        mSupportMatrix = matrix;
        mOverlayPaint = paint;

        invalidate();
    }

    @Override
    public void filterChanged(Paint paint) {
        mFilterPaint = paint;

        invalidate();
    }

    @Override
    public void frameChanged(Bitmap bitmap, Matrix matrix) {
        mSupportBitmap = bitmap;
        mSupportMatrix = matrix;

        invalidate();
    }

    @Override
    public void textAdded(List<EditorText> texts) {
        mTexts = texts;

        invalidate();
    }

    @Override
    public void stickerAdded(List<EditorSticker> stickers) {
        mStickers = stickers;

        invalidate();
    }

    @Override
    public void brushDown(Paint paint, Path path) {
        mDrawingPaint = paint;
        mDrawingPath = path;

        invalidate();
    }

    @Override
    public void brushMove(Paint paint, Path path) {
        mDrawingPaint = paint;
        mDrawingPath = path;

        invalidate();
    }

    @Override
    public void brushUp(List<Drawing> drawings) {
        mDrawings = drawings;

        invalidate();
    }

    @Override
    public void showProgress() {
        mLoadingDialog.show();
    }

    @Override
    public void hideProgress() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void imageChanged(Bitmap bitmap) {
        mAlteredImageBitmap = bitmap;

        invalidate();
    }
}