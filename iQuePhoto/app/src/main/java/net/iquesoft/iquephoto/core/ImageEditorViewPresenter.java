package net.iquesoft.iquephoto.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.MotionEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.core.enums.EditorMode;
import net.iquesoft.iquephoto.core.enums.EditorTool;
import net.iquesoft.iquephoto.core.model.Drawing;
import net.iquesoft.iquephoto.core.model.EditorImage;
import net.iquesoft.iquephoto.core.model.EditorLinearTiltShift;
import net.iquesoft.iquephoto.core.model.EditorRadialTiltShift;
import net.iquesoft.iquephoto.core.model.EditorSticker;
import net.iquesoft.iquephoto.core.model.EditorText;
import net.iquesoft.iquephoto.core.model.EditorVignette;
import net.iquesoft.iquephoto.models.Text;
import net.iquesoft.iquephoto.tasks.ImageCacheSaveTask;
import net.iquesoft.iquephoto.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

import static net.iquesoft.iquephoto.core.enums.EditorTool.NONE;

@InjectViewState
public class ImageEditorViewPresenter extends MvpPresenter<EditorView> {
    private float mLastX;
    private float mLastY;

    private int mViewWidth;
    private int mViewHeight;

    private Context mContext;

    private EditorText mTouchedText;
    private EditorSticker mTouchedSticker;

    private EditorTool mCurrentTool = NONE;
    private EditorMode mCurrentMode = EditorMode.NONE;

    private EditorVignette mVignette;
    private EditorRadialTiltShift mRadialTiltShift;
    private EditorLinearTiltShift mLinearTiltShift;
    private EditorListener mEditorListener;

    private Bitmap mImageBitmap;
    private Bitmap mSupportBitmap;
    private Bitmap mBlurredBitmap;

    private Paint mDrawingPaint = new Paint();
    private Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mFilterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mAdjustPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path mDrawingPath = new Path();

    private RectF mImageRect;

    private Matrix mImageMatrix = new Matrix();
    private Matrix mSupportMatrix = new Matrix();
    private Matrix mTransformMatrix = new Matrix();

    private ColorMatrix mColorMatrix = new ColorMatrix();

    private List<EditorText> mTexts = new ArrayList<>();
    private List<EditorSticker> mStickers = new ArrayList<>();
    private List<Drawing> mDrawings = new ArrayList<>();
    private List<EditorImage> mImages = new ArrayList<>();

    private PublishSubject<MotionEvent> mTouchSubject = PublishSubject.create();

    ImageEditorViewPresenter(@NonNull Context context) {
        mOverlayPaint.setAlpha(150);
        initDrawingPaint();

        mContext = context;

        initializeMotionEventObservables();
    }

    private void initDrawingPaint() {
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setColor(Color.BLUE);
        mDrawingPaint.setStrokeCap(Paint.Cap.ROUND);
        mDrawingPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawingPaint.setStrokeWidth(Drawing.DEFAULT_STROKE_WIDTH);
    }

    private void initializeMotionEventObservables() {
        Observable<MotionEvent> touchObservable = mTouchSubject.asObservable();
        Observable<MotionEvent> actionDownObservable =
                touchObservable.filter(event ->
                        event.getActionMasked() == MotionEvent.ACTION_DOWN);
        Observable<MotionEvent> actionPointerDownObservable =
                touchObservable.filter(event ->
                        event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN);
        Observable<MotionEvent> actionMoveObservable =
                touchObservable.filter(event ->
                        event.getActionMasked() == MotionEvent.ACTION_MOVE);
        Observable<MotionEvent> actionUpObservable =
                touchObservable.filter(event ->
                        event.getActionMasked() == MotionEvent.ACTION_UP);
        Observable<MotionEvent> actionPointerUpObservable =
                touchObservable.filter(event ->
                        event.getActionMasked() == MotionEvent.ACTION_POINTER_UP);
        actionDownObservable.subscribe(event -> {
            actionDown(event);
            actionMoveObservable.
                    takeUntil(actionUpObservable
                            .doOnNext(upEvent ->
                                    actionUp()
                            ))
                    .subscribe(this::actionMove);
        });
        actionPointerDownObservable.subscribe(event -> {
                    actionPointerDown(event);
                    actionMoveObservable.takeUntil(
                            actionPointerUpObservable.doOnNext(pointerUpEvent ->
                                    actionPointerUp()
                            ))
                            .subscribe(this::actionMove);
                }
        );
    }

    private void actionDown(MotionEvent event) {
        Log.i("Observable", "Action: Down.");
        switch (mCurrentTool) {
            case NONE:
                // FIXME: setupImageOnView(...)
                getViewState().showOriginalImage(true);
                break;
            case DRAWING:
                brushActionDown(event);
                break;
            case TEXT:
                textActionDown(event);
                break;
            case STICKERS:
                stickerActionDown(event);
                break;
            case VIGNETTE:
                mVignette.actionDown(event);
                getViewState().onVignetteUpdated(mVignette);
                break;
            case RADIAL_TILT_SHIFT:
                mRadialTiltShift.actionDown(event);
                getViewState().onRadialTiltShiftUpdated(mRadialTiltShift);
                break;
        }
    }

    private void actionPointerDown(MotionEvent event) {
        Log.i("Observable", "Action: Pointer Down.");
        switch (mCurrentTool) {
            case NONE:
                break;
            case VIGNETTE:
                mVignette.actionPointerDown(event);
                getViewState().onVignetteUpdated(mVignette);
                break;
            case RADIAL_TILT_SHIFT:
                mRadialTiltShift.actionPointerDown(event);
                getViewState().onRadialTiltShiftUpdated(mRadialTiltShift);
                break;
            case LINEAR_TILT_SHIFT:
                mLinearTiltShift.actionPointerDown(event);
                getViewState().onLinearTiltShiftUpdated(mLinearTiltShift);
                break;
        }
    }

    private void actionMove(MotionEvent event) {
        Log.i("Observable", "Action: Move.");
        switch (mCurrentTool) {
            case NONE:
                break;
            case DRAWING:
                brushActionMove(event);
                break;
            case TEXT:
                textActionMove(event);
                break;
            case STICKERS:
                stickerActionMove(event);
                break;
            case VIGNETTE:
                mVignette.actionMove(event);
                getViewState().onVignetteUpdated(mVignette);
                break;
            case RADIAL_TILT_SHIFT:
                mRadialTiltShift.actionMove(event);
                getViewState().onRadialTiltShiftUpdated(mRadialTiltShift);
                break;
            case LINEAR_TILT_SHIFT:
                mLinearTiltShift.actionMove(event);
                getViewState().onLinearTiltShiftUpdated(mLinearTiltShift);
                break;
        }
    }

    private void actionPointerUp() {
        Log.i("Observable", "Action: Pointer Up.");
        mCurrentMode = EditorMode.NONE;
        switch (mCurrentTool) {
            case VIGNETTE:
                mVignette.actionPointerUp();
                getViewState().onVignetteUpdated(mVignette);
                break;
            case RADIAL_TILT_SHIFT:
                mRadialTiltShift.actionPointerUp();
                getViewState().onRadialTiltShiftUpdated(mRadialTiltShift);
                break;
            case LINEAR_TILT_SHIFT:
                mLinearTiltShift.actionPointerUp();
                getViewState().onLinearTiltShiftUpdated(mLinearTiltShift);
                break;
        }
    }

    private void actionUp() {
        Log.i("Observable", "Action: Up.");
        mCurrentMode = EditorMode.NONE;
        switch (mCurrentTool) {
            case NONE:
                // FIXME: setupImageOnView(...)
                getViewState().showOriginalImage(false);
                break;
            case DRAWING:
                brushActionUp();
                break;
            case TEXT:
                if (mTouchedText != null) {
                    mTouchedText.resetHelperFrameOpacity();
                }
                break;
            case STICKERS:
                if (mTouchedSticker != null) {
                    mTouchedSticker.setStickerTouched(false);

                    getViewState().updateView();
                }
                break;
            case VIGNETTE:
                mVignette.actionUp();
                getViewState().onVignetteUpdated(mVignette);
                break;
            case RADIAL_TILT_SHIFT:
                mRadialTiltShift.actionUp();
                getViewState().onRadialTiltShiftUpdated(mRadialTiltShift);
                break;
            case LINEAR_TILT_SHIFT:
                mLinearTiltShift.actionUp();
                getViewState().onLinearTiltShiftUpdated(mLinearTiltShift);
                break;
        }
    }

    void setupBitmapOnView(Bitmap bitmap, int width, int height) {
        mViewWidth = width;
        mViewHeight = height;

        if (mImageBitmap == null) {
            mImageBitmap = bitmap;
        }

        RectF viewRect = new RectF(0, 0, mViewWidth, mViewHeight);
        mImageRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

        LogHelper.logRect("mViewRect", viewRect);

        mImageMatrix.reset();
        mImageMatrix.setRectToRect(mImageRect, viewRect, Matrix.ScaleToFit.CENTER);
        mImageMatrix.mapRect(mImageRect);

        mTransformMatrix.set(mImageMatrix);

        if (mVignette == null) {
            mVignette = new EditorVignette(mViewWidth, mViewHeight);
        }

        LogHelper.logRect("mImageRect", mImageRect);
        LogHelper.logMatrix("mImageMatrix", mImageMatrix);

        getViewState().setupImage(bitmap, mImageMatrix, mImageRect);
    }

    void viewTouched(MotionEvent event) {
        mTouchSubject.onNext(event);
    }

    void applyChanges() {
        switch (mCurrentTool) {
            case STICKERS:
                for (EditorSticker sticker : mStickers) {
                    sticker.setHelpFrameEnabled(false);
                }
                break;
            case TEXT:
                for (EditorText text : mTexts) {
                    text.setHelpFrameEnabled(false);
                }
                break;
            case VIGNETTE:
                mVignette.setHelpOvalEnabled(false);
                break;
            case RADIAL_TILT_SHIFT:
                mRadialTiltShift.setHelpOvalEnabled(false);
                break;
            case LINEAR_TILT_SHIFT:
                mLinearTiltShift.setHelpOvalEnabled(false);
                break;
        }

        getViewState().onApplyChanges();
    }

    void applyChanges(@NonNull Bitmap bitmap) {
        Bitmap b = Bitmap.createBitmap(bitmap);
        mImages.add(new EditorImage(mCurrentTool, b));

        ImageCacheSaveTask imageCacheSaveTask = new ImageCacheSaveTask(mContext, bitmap);
        imageCacheSaveTask.setOnImageLoadedListener(new ImageCacheSaveTask.OnImageCacheSaveListener() {
            @Override
            public void onSaveStarted() {
                Log.i("ImageCache", "Started");
            }

            @Override
            public void onImageSaved(Uri uri) {
                mEditorListener.onAppliedImageSaved(uri);
                Log.i("ImageCache", "Finished");

                setupBitmapOnView(getAlteredBitmap(), mViewWidth, mViewHeight);
            }
        });

        imageCacheSaveTask.execute();

        mEditorListener.hasChanges(mImages.size());
    }

    public void setFilter(ColorMatrix colorMatrix) {
        mFilterPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        getViewState().onFilterChanged(mFilterPaint);
    }

    void changeFilterIntensity(int value) {
        mFilterPaint.setAlpha(value);

        getViewState().onFilterChanged(mFilterPaint);
    }

    void changeOverlayIntensity(int value) {
        mOverlayPaint.setAlpha(value);
    }

    void changeVignetteMask(int value) {
        mVignette.updateMask(value);

        getViewState().onVignetteUpdated(mVignette);
    }

    void changeBrightness(int value) {
        float brightness = value / 2;

        mColorMatrix.reset();
        mColorMatrix.set(new float[]{
                1, 0, 0, 0, brightness,
                0, 1, 0, 0, brightness,
                0, 0, 1, 0, brightness,
                0, 0, 0, 1, 0
        });

        mAdjustPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));

        getViewState().onImageAdjusted(mAdjustPaint);
    }

    void changeContrast(int value) {
        float input = value / 100f;
        float scale = input + 1f;
        float contrast = (-0.5f * scale + 0.5f) * 255f;

        mColorMatrix.reset();
        mColorMatrix.set(
                new float[]{
                        scale, 0, 0, 0, contrast,
                        0, scale, 0, 0, contrast,
                        0, 0, scale, 0, contrast,
                        0, 0, 0, 1, 0}
        );

        mAdjustPaint.setColorFilter(
                new ColorMatrixColorFilter(mColorMatrix));

        getViewState().onImageAdjusted(mAdjustPaint);
    }

    void changeSaturation(int value) {
        float saturation = (value + 100) / 100f;

        mColorMatrix.reset();
        mColorMatrix.setSaturation(saturation);

        mAdjustPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));

        getViewState().onImageAdjusted(mAdjustPaint);
    }

    void changeWarmth(int value) {
        float warmth = (value / 220) / 2;

        mColorMatrix.reset();
        mColorMatrix.set(
                new float[]{
                        1, 0, 0, warmth, 0,
                        0, 1, 0, warmth / 2, 0,
                        0, 0, 1, warmth / 4, 0,
                        0, 0, 0, 1, 0
                });
        mAdjustPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));

        getViewState().onImageAdjusted(mAdjustPaint);
    }

    void changeBrushSize(float size) {
        mDrawingPaint.setStrokeWidth(size);
    }

    void changeBrushColor(int color) {
        mDrawingPaint.setColor(color);
    }

    void changeStraightenTransform(int value) {
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

        getViewState().onStraightenTransformChanged(mTransformMatrix);
    }

    public void setFrame(Bitmap bitmap) {
        getViewState().onFrameChanged(bitmap, getSupportMatrix(bitmap));
    }

    public void setOverlay(Bitmap bitmap) {
        mSupportBitmap = bitmap;

        getViewState().onOverlayChanged(bitmap, getSupportMatrix(bitmap), mOverlayPaint);
    }

    void addText(Text text) {
        EditorText editorText = new EditorText(text);
        editorText.setX(mImageRect.centerX());
        editorText.setY(mImageRect.centerY());

        mTexts.add(editorText);

        getViewState().onTextAdded(mTexts);
    }

    void addSticker(Bitmap bitmap) {
        mStickers.add(new EditorSticker(bitmap, mImageRect));

        getViewState().onStickerAdded(mStickers);
    }

    public void changeTool(EditorTool tool) {
        mCurrentTool = tool;

        if (!mTexts.isEmpty()) {
            mTexts.clear();
        }
        if (!mStickers.isEmpty()) {
            mStickers.clear();
        }

        if (!mDrawings.isEmpty()) {
            mDrawings.clear();
        }

        switch (mCurrentTool) {
            case VIGNETTE:
                mVignette.updateRect(mImageRect);
                mVignette.setHelpOvalEnabled(true);
                getViewState().onVignetteUpdated(mVignette);
                break;
            case RADIAL_TILT_SHIFT:
                if (mRadialTiltShift == null) {
                    mRadialTiltShift = new EditorRadialTiltShift(mViewWidth, mViewHeight);
                }
                mBlurredBitmap =
                        getBlurBitmap(
                                mContext, getAlteredBitmap(),
                                mImageBitmap.getWidth(),
                                mImageBitmap.getHeight()
                        );
                mRadialTiltShift.updateRect(mImageRect);
                mRadialTiltShift.updateBlurBitmap(mBlurredBitmap);
                mRadialTiltShift.setHelpOvalEnabled(true);
                getViewState().onRadialTiltShiftUpdated(mRadialTiltShift);
                break;
            case LINEAR_TILT_SHIFT:
                if (mLinearTiltShift == null) {
                    mLinearTiltShift = new EditorLinearTiltShift(mViewWidth, mViewHeight);
                }
                mBlurredBitmap =
                        getBlurBitmap(
                                mContext, getAlteredBitmap(),
                                mImageBitmap.getWidth(),
                                mImageBitmap.getHeight()
                        );
                mLinearTiltShift.updateRect(mImageRect);
                mLinearTiltShift.updateBlurBitmap(mBlurredBitmap);
                mLinearTiltShift.setHelpOvalEnabled(true);
                getViewState().onLinearTiltShiftUpdated(mLinearTiltShift);
                break;
            default:
                mColorMatrix.reset();
                mAdjustPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
                break;
        }

        getViewState().onToolChanged(tool);
    }

    void setEditorListener(EditorListener editorListener) {
        mEditorListener = editorListener;
    }

    void undo() {
        if (!mImages.isEmpty()) {
            mImages.remove(mImages.size() - 1);
            mEditorListener.hasChanges(mImages.size());
            setupBitmapOnView(getAlteredBitmap(), mViewWidth, mViewHeight);
        } else {
            setupBitmapOnView(mImageBitmap, mViewWidth, mViewHeight);
        }
    }

    private Matrix getSupportMatrix(Bitmap bitmap) {
        float sX = mImageRect.width() / bitmap.getWidth();
        float sY = mImageRect.height() / bitmap.getHeight();

        LogHelper.logMatrix("mSupportMatrix before (View)", mSupportMatrix);

        mSupportMatrix.reset();
        mSupportMatrix.postScale(sX, sY);
        mSupportMatrix.postTranslate(mImageRect.left, mImageRect.top);

        LogHelper.logMatrix("mSupportMatrix after (View)", mSupportMatrix);

        return mSupportMatrix;
    }

    private void textActionDown(MotionEvent event) {
        for (int i = mTexts.size() - 1; i >= 0; i--) {
            EditorText editorText = mTexts.get(i);

            if (editorText.isInside(event)) {
                mTouchedText = editorText;
                mCurrentMode = EditorMode.MOVE;

                mTouchedText.setHelperFrameOpacity();

                mLastX = event.getX();
                mLastY = event.getY();

                return;
            } else if (editorText.isInDeleteHandleButton(event)) {
                mTouchedText = null;
                mCurrentMode = EditorMode.NONE;

                mTexts.remove(i);
                getViewState().updateView();
                return;
            } else if (editorText.isInResizeAndScaleHandleButton(event)) {
                mTouchedText = editorText;

                mTouchedText.setHelperFrameOpacity();

                mLastX = editorText.getRotateAndScaleHandleDstRect().centerX();
                mLastY = editorText.getRotateAndScaleHandleDstRect().centerY();

                mCurrentMode = EditorMode.ROTATE_AND_SCALE;
                return;

            } else if (editorText.isInFrontHandleButton(event)) {
                EditorText temp = mTexts.remove(i);
                mTexts.add(temp);
                getViewState().updateView();
                return;
            } else if (editorText.isInTransparencyHandleButton(event)) {
                mEditorListener.onTransparencyHandleButtonClicked(editorText.getPaint());
                return;
            }
        }

        mTouchedText = null;
        mCurrentMode = EditorMode.NONE;
    }

    private void stickerActionDown(MotionEvent event) {
        for (int i = mStickers.size() - 1; i >= 0; i--) {
            EditorSticker editorSticker = mStickers.get(i);

            if (editorSticker.isInside(event)) {
                mTouchedSticker = editorSticker;
                mCurrentMode = EditorMode.MOVE;

                mTouchedSticker.setStickerTouched(true);

                mLastX = event.getX();
                mLastY = event.getY();

                return;
            } else if (editorSticker.isInDeleteHandleButton(event)) {
                mTouchedSticker = null;

                mCurrentMode = EditorMode.NONE;

                mStickers.remove(i);

                getViewState().updateView();
                return;
            } else if (editorSticker.isInScaleAndRotateHandleButton(event)) {
                mTouchedSticker = editorSticker;
                mCurrentMode = EditorMode.ROTATE_AND_SCALE;

                mTouchedSticker.setStickerTouched(true);

                mLastX = event.getX();
                mLastY = event.getY();
                return;
            } else if (editorSticker.isInFrontHandleButton(event)) {
                mCurrentMode = EditorMode.NONE;

                mStickers.add(mStickers.remove(i));

                getViewState().updateView();
                return;
            } else if (editorSticker.isInTransparencyHandleButton(event)) {
                mEditorListener.onTransparencyHandleButtonClicked(editorSticker.getPaint());
                return;
            }
        }

        mTouchedSticker = null;

        mCurrentMode = EditorMode.NONE;
    }

    private void brushActionDown(MotionEvent event) {
        Log.i("Drawing", "Brush down");
        mLastX = event.getX();
        mLastY = event.getY();

        mDrawingPath.reset();

        mDrawingPath.moveTo(mLastX, mLastY);

        getViewState().updateDrawing(mDrawingPaint, mDrawingPath);
    }

    private void brushActionMove(MotionEvent event) {
        Log.i("Drawing", "Brush move");

        float dX = event.getX() + mLastX;
        float dY = event.getY() + mLastY;

        mDrawingPath.quadTo(mLastX, mLastY, dX / 2, dY / 2);

        mLastX = event.getX();
        mLastY = event.getY();

        getViewState().updateDrawing(mDrawingPaint, mDrawingPath);
    }

    private void brushActionUp() {
        Log.i("Drawing", "Brush up");

        mDrawingPath.lineTo(mLastX, mLastY);
        mDrawings.add(new Drawing(new Paint(mDrawingPaint), new Path(mDrawingPath)));

        mDrawingPath.reset();

        getViewState().updateDrawing(mDrawings);
    }

    private void textActionMove(MotionEvent event) {
        if (mTouchedText != null) {
            switch (mCurrentMode) {
                case MOVE:
                    float distanceX = event.getX() - mLastX;
                    float distanceY = event.getY() - mLastY;

                    float newX = mTouchedText.getX() + distanceX;
                    float newY = mTouchedText.getY() + distanceY;

                    mTouchedText.setX(newX);
                    mTouchedText.setY(newY);

                    mLastX = event.getX();
                    mLastY = event.getY();

                    break;
                case ROTATE_AND_SCALE:
                    mTouchedText.updateRotateAndScale(
                            getDeltaX(event),
                            getDeltaY(event)
                    );

                    mLastX = event.getX();
                    mLastY = event.getY();

                    break;
            }

            getViewState().updateView();
        }
    }

    private void stickerActionMove(MotionEvent event) {
        if (mTouchedSticker != null) {
            switch (mCurrentMode) {
                case MOVE:
                    mTouchedSticker.actionMove(
                            getDeltaX(event),
                            getDeltaY(event)
                    );

                    mLastX = event.getX();
                    mLastY = event.getY();

                    break;
                case ROTATE_AND_SCALE:
                    mTouchedSticker.updateRotateAndScale(
                            getDeltaX(event),
                            getDeltaY(event)
                    );

                    mLastX = event.getX();
                    mLastY = event.getY();

                    break;
            }

            getViewState().updateView();
        }
    }

    private Bitmap getAlteredBitmap() {
        if (!mImages.isEmpty()) {
            return mImages.get(mImages.size() - 1).getBitmap();
        }

        return mImageBitmap;
    }

    private Bitmap getBlurBitmap(Context context, Bitmap bitmap, int width, int height) {
        Bitmap src = bitmap.copy(bitmap.getConfig(), true);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(src, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(scaledBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, scaledBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(10f);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    private float getDeltaX(MotionEvent event) {
        return event.getX() - mLastX;
    }

    private float getDeltaY(MotionEvent event) {
        return event.getY() - mLastY;
    }
}
