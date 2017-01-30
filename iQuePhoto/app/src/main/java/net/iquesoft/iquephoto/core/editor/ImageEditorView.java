package net.iquesoft.iquephoto.core.editor;

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
import android.view.MotionEvent;
import android.view.View;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.core.editor.model.Drawing;
import net.iquesoft.iquephoto.core.editor.model.EditorLinearTiltShift;
import net.iquesoft.iquephoto.core.editor.model.EditorSticker;
import net.iquesoft.iquephoto.core.editor.model.EditorText;
import net.iquesoft.iquephoto.core.editor.model.EditorRadialTiltShift;
import net.iquesoft.iquephoto.core.editor.model.EditorVignette;
import net.iquesoft.iquephoto.models.Text;

import java.util.List;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.NONE;

public class ImageEditorView extends View implements EditorView {
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
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mPresenter.setupImage(getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final Bitmap bitmap;

        if (mAlteredImageBitmap != null) {
            bitmap = mAlteredImageBitmap;
        } else {
            bitmap = mImageBitmap;
        }

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
    public void imageAdjusted(Paint paint) {
        if (mAdjustPaint == null) {
            mAdjustPaint = paint;
        }

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
        if (mFilterPaint == null) {
            mFilterPaint = paint;
        }

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
        if (mTexts == null) {
            mTexts = texts;
        }

        invalidate();
    }

    @Override
    public void stickerAdded(List<EditorSticker> stickers) {
        if (mStickers == null) {
            mStickers = stickers;
        }

        invalidate();
    }

    @Override
    public void updateVignette(EditorVignette vignette) {
        if (mVignette == null) {
            mVignette = vignette;
        }

        invalidate();
    }

    @Override
    public void updateRadialTiltShift(EditorRadialTiltShift radialTiltShift) {
        if (mRadialTiltShift == null) {
            mRadialTiltShift = radialTiltShift;
        }

        invalidate();
    }

    @Override
    public void updateLinearTiltShift(EditorLinearTiltShift linearTiltShift) {
        if (mLinearTiltShift == null) {
            mLinearTiltShift = linearTiltShift;
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
    public void imageChanged(Bitmap bitmap) {
        mAlteredImageBitmap = bitmap;

        invalidate();
    }

    @Override
    public void updateView() {
        invalidate();
    }
}