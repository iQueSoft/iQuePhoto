package net.iquesoft.iquephoto.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.core.enums.EditorTool;
import net.iquesoft.iquephoto.core.model.Drawing;
import net.iquesoft.iquephoto.core.model.EditorLinearTiltShift;
import net.iquesoft.iquephoto.core.model.EditorSticker;
import net.iquesoft.iquephoto.core.model.EditorText;
import net.iquesoft.iquephoto.core.model.EditorRadialTiltShift;
import net.iquesoft.iquephoto.core.model.EditorVignette;
import net.iquesoft.iquephoto.models.Text;

import java.util.List;

import static net.iquesoft.iquephoto.core.enums.EditorTool.NONE;

public class ImageEditorView extends View implements EditorView {
    private final static String TAG = ImageEditorView.class.getSimpleName();

    @InjectPresenter
    ImageEditorViewPresenter mPresenter;

    @ProvidePresenter
    ImageEditorViewPresenter provideImageEditorViewPresenter() {
        return new ImageEditorViewPresenter(getContext());
    }

    private MvpDelegate mParentDelegate;
    private MvpDelegate<ImageEditorView> mMvpDelegate;

    private boolean mIsOriginalImageDisplayed;

    private Bitmap mImageBitmap;
    private Bitmap mSupportBitmap;
    private Bitmap mAlteredImageBitmap;

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

    private EditorVignette mVignette;
    private EditorRadialTiltShift mRadialTiltShift;
    private EditorLinearTiltShift mLinearTiltShift;

    private List<Drawing> mDrawings;
    private List<EditorText> mTexts;
    private List<EditorSticker> mStickers;

    public ImageEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMvpDelegate(MvpDelegate patentDelegate) {
        mParentDelegate = patentDelegate;

        getMvpDelegate().onCreate();
        getMvpDelegate().onAttach();

        Log.i(TAG, "setMvpDelegate(...)");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout(...)");
        mPresenter.setupBitmapOnView(getAlteredImageBitmap(), getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw(...)");

        final Bitmap bitmap = getAlteredImageBitmap();

        if (mImageRect != null && bitmap != null) {
            canvas.clipRect(mImageRect);

            canvas.drawBitmap(bitmap, mImageMatrix, mBitmapPaint);

            switch (mCurrentTool) {
                case NONE:
                    if (mIsOriginalImageDisplayed) {
                        canvas.drawBitmap(mImageBitmap, mImageMatrix, mBitmapPaint);
                    } else {
                        canvas.drawBitmap(bitmap, mImageMatrix, mBitmapPaint);
                    }
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
                case RADIAL_TILT_SHIFT:
                    mRadialTiltShift.draw(canvas, bitmap, mImageMatrix, mBitmapPaint);
                    break;
                case LINEAR_TILT_SHIFT:
                    mLinearTiltShift.draw(canvas, bitmap, mImageMatrix, mBitmapPaint);
                    break;
                default:
                    canvas.drawBitmap(bitmap, mImageMatrix, mAdjustPaint);
                    break;
            }
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

    public MvpDelegate<ImageEditorView> getMvpDelegate() {
        if (mMvpDelegate != null) {
            return mMvpDelegate;
        }

        mMvpDelegate = new MvpDelegate<>(this);
        mMvpDelegate.setParentDelegate(mParentDelegate, String.valueOf(getId()));

        return mMvpDelegate;
    }

    public void setImageBitmap(@NonNull Bitmap bitmap) {
        Log.i(TAG, "setImageBitmap(...)");
        mImageBitmap = bitmap;
    }

    public Bitmap getAlteredImageBitmap() {
        if (mAlteredImageBitmap != null) {
            return mAlteredImageBitmap;
        }

        return mImageBitmap;
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

    public void setUndoListener(EditorListener editorListener) {
        mPresenter.setEditorListener(editorListener);
    }

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

    public void setFilterIntensity(@IntRange(from = -100, to = 100) int value) {
        mPresenter.changeFilterIntensity(value);
    }

    public void setVignetteIntensity(@IntRange(from = -100, to = 100) int value) {
        mPresenter.changeVignetteMask(value);
    }

    public void setOverlayIntensity(@IntRange(from = -100, to = 100) int value) {
        mPresenter.changeOverlayIntensity(value);
    }

    public void setBrightnessValue(@IntRange(from = -100, to = 100) int value) {
        mPresenter.changeBrightness(value);
    }

    public void setContrastValue(@IntRange(from = -100, to = 100) int value) {
        mPresenter.changeContrast(value);
    }

    public void setSaturationValue(@IntRange(from = -100, to = 100) int value) {
        mPresenter.changeSaturation(value);
    }

    public void setWarmthValue(@IntRange(from = -100, to = 100) int value) {
        mPresenter.changeWarmth(value);
    }

    public void setStraightenTransformValue(@IntRange(from = -30, to = 30) int value) {
        mPresenter.changeStraightenTransform(value);
    }

    public void setBrushColor(@ColorInt int color) {
        mPresenter.changeBrushColor(color);
    }

    public void setBrushSize(float size) {
        mPresenter.changeBrushSize(size);
    }

    private void drawTexts(Canvas canvas) {
        if (mTexts != null) {
            for (EditorText text : mTexts) {
                text.draw(canvas);
            }
        }
    }

    private void drawStickers(Canvas canvas) {
        if (mStickers != null) {
            for (EditorSticker sticker : mStickers) {
                sticker.draw(canvas);
            }
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

    @Override
    public void setupImage(Bitmap bitmap, Matrix imageMatrix, RectF imageRect) {
        if (mImageBitmap == null) {
            mImageBitmap = bitmap;
        } else {
            mAlteredImageBitmap = bitmap;
        }

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
    public void onToolChanged(EditorTool tool) {
        mCurrentTool = tool;

        invalidate();
    }

    @Override
    public void onImageAdjusted(Paint paint) {
        if (mAdjustPaint == null) {
            mAdjustPaint = paint;
        }

        invalidate();
    }

    @Override
    public void onOverlayChanged(Bitmap bitmap, Matrix matrix, Paint paint) {
        mSupportBitmap = bitmap;
        mSupportMatrix = matrix;
        mOverlayPaint = paint;

        invalidate();
    }

    @Override
    public void onFilterChanged(Paint paint) {
        if (mFilterPaint == null) {
            mFilterPaint = paint;
        }

        invalidate();
    }

    @Override
    public void onFrameChanged(Bitmap bitmap, Matrix matrix) {
        mSupportBitmap = bitmap;
        mSupportMatrix = matrix;

        invalidate();
    }

    @Override
    public void onTextAdded(List<EditorText> texts) {
        if (mTexts == null) {
            mTexts = texts;
        }

        invalidate();
    }

    @Override
    public void onStickerAdded(List<EditorSticker> stickers) {
        if (mStickers == null) {
            mStickers = stickers;
        }

        invalidate();
    }

    @Override
    public void onVignetteUpdated(EditorVignette vignette) {
        if (mVignette == null) {
            mVignette = vignette;
        }

        invalidate();
    }

    @Override
    public void onRadialTiltShiftUpdated(EditorRadialTiltShift radialTiltShift) {
        if (mRadialTiltShift == null) {
            mRadialTiltShift = radialTiltShift;
        }

        invalidate();
    }

    @Override
    public void onLinearTiltShiftUpdated(EditorLinearTiltShift linearTiltShift) {
        if (mLinearTiltShift == null) {
            mLinearTiltShift = linearTiltShift;
        }

        invalidate();
    }

    @Override
    public void onStraightenTransformChanged(Matrix transformMatrix) {
        if (mTransformMatrix == null) {
            mTransformMatrix = transformMatrix;
        }

        invalidate();
    }

    @Override
    public void updateDrawing(Paint paint, Path path) {
        if (mDrawingPaint == null && mDrawingPath == null) {
            mDrawingPaint = paint;
            mDrawingPath = path;
        }

        invalidate();
    }

    @Override
    public void updateDrawing(List<Drawing> drawings) {
        if (mDrawings == null) {
            mDrawings = drawings;
        }

        invalidate();
    }

    @Override
    public void updateView() {
        invalidate();
    }

    @Override
    public void onApplyChanges() {
        invalidate();

        setDrawingCacheEnabled(true);
        mPresenter.applyChanges(getDrawingCache());
    }

    @Override
    public void enableDrawingCache() {
        setDrawingCacheEnabled(false);
    }
}