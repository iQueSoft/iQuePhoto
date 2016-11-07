package net.iquesoft.iquephoto.core;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Sticker;
import net.iquesoft.iquephoto.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

import static net.iquesoft.iquephoto.core.EditorCommand.NONE;

public class ImageEditorView extends ImageView {

    private float mBrushSize;

    private boolean mIsInitialized;
    private boolean mIsShowOriginalImage;

    private boolean mIsInResize;
    private boolean mIsInSide;
    private boolean mIsInRotate;

    private Bitmap mSourceBitmap;

    private Bitmap mOverlayBitmap;
    private Bitmap mFrameBitmap;

    private EditorFrame mEditorFrame;

    private EditorText mCurrentEditorText;
    private EditorSticker mCurrentEditorSticker;

    private Context mContext;

    private Paint mImagePaint;
    private Paint mFilterPaint;
    private Paint mContrastPaint;
    private Paint mWarmthPaint;
    private Paint mBrightnessPaint;
    private Paint mOverlayPaint;
    private Paint mDrawingPaint;
    private Paint mDrawingCirclePaint;

    private Path mDrawingPath;
    private Path mOriginalDrawingPath;
    private Path mDrawingCirclePath;

    private List<EditorImage> mImagesList;
    private List<EditorText> mTextsList;
    private List<Drawing> mDrawingList;
    private List<EditorSticker> mStickersList;

    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private float mScale = 1.0f;
    private float mAngle = 0.0f;
    private float mImgWidth = 0.0f;
    private float mImgHeight = 0.0f;

    private float mContrastValue = 0;
    private float mBrightnessValue = 0;
    private float mWarmthValue = 0;

    private EditorCommand mCommand = NONE;

    private IUndoListener mUndoListener;

    private Matrix mMatrix = null;

    private RectF mImageRect;
    private PointF mCenter = new PointF();

    private float mLastX, mLastY;

    private TouchArea mTouchArea = TouchArea.OUT_OF_BOUNDS;

    private int mTouchPadding = 0;

    private MaterialDialog mProgressDialog;

    public ImageEditorView(Context context) {
        this(context, null);
    }

    public ImageEditorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageEditorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;

        mTextsList = new ArrayList<>();
        mImagesList = new ArrayList<>();
        mStickersList = new ArrayList<>();

        mImagePaint = new Paint();
        mFilterPaint = new Paint();
        mWarmthPaint = new Paint();
        mContrastPaint = new Paint();
        mBrightnessPaint = new Paint();
        mOverlayPaint = new Paint();

        mEditorFrame = new EditorFrame(context);

        mImagePaint.setFilterBitmap(true);

        mMatrix = new Matrix();

        mScale = 1.0f;

        initDrawing();
        initProgressDialog();
    }

    private void initDrawing() {
        mDrawingList = new ArrayList<>();

        mDrawingPaint = new Paint();
        mDrawingCirclePaint = new Paint();

        mDrawingPath = new Path();
        mOriginalDrawingPath = new Path();
        mDrawingCirclePath = new Path();

        mDrawingPaint.setAntiAlias(true);
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setColor(Drawing.DEFAULT_COLOR);
        mDrawingPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawingPaint.setStrokeCap(Paint.Cap.ROUND);
        mDrawingPaint.setStrokeWidth(Drawing.DEFAULT_STROKE_WIDTH);

        mDrawingCirclePaint.setAntiAlias(true);
        mDrawingCirclePaint.setColor(Drawing.DEFAULT_COLOR);
        mDrawingCirclePaint.setStyle(Paint.Style.STROKE);
        mDrawingCirclePaint.setStrokeJoin(Paint.Join.MITER);
        mDrawingCirclePaint.setStrokeWidth(10f);
    }

    private void initProgressDialog() {
        mProgressDialog = new MaterialDialog.Builder(mContext)
                .content(R.string.processing)
                .progress(true, 0)
                .widgetColor(getResources().getColor(android.R.color.black))
                .contentColor(getResources().getColor(android.R.color.black))
                .canceledOnTouchOutside(false)
                .build();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mIsInitialized) {
            setMatrix();
            if (mIsShowOriginalImage) {
                canvas.drawBitmap(mSourceBitmap, mMatrix, mImagePaint);
            } else {
                if (mImagesList.size() > 0)
                    canvas.drawBitmap(getAlteredBitmap(), mMatrix, mImagePaint);
                else
                    canvas.drawBitmap(mSourceBitmap, mMatrix, mImagePaint);
            }
        }

        switch (mCommand) {
            case FILTERS:
                if (mImagesList.size() > 0) {
                    canvas.drawBitmap(getAlteredBitmap(), mMatrix, mFilterPaint);
                } else {
                    canvas.drawBitmap(mSourceBitmap, mMatrix, mFilterPaint);
                }
                break;
            case DRAWING:
                if (mDrawingList.size() > 0) {
                    for (Drawing drawing : mDrawingList) {
                        canvas.drawPath(drawing.getPath(), drawing.getPaint());
                    }
                }
                if (!mDrawingPath.isEmpty())
                    canvas.drawPath(mDrawingPath, mDrawingPaint);
                break;
            case BRIGHTNESS:
                if (mBrightnessValue != 0) {
                    if (mImagesList.size() > 0) {
                        canvas.drawBitmap(getAlteredBitmap(), mMatrix, mBrightnessPaint);
                    } else {
                        canvas.drawBitmap(mSourceBitmap, mMatrix, mBrightnessPaint);
                    }
                }
                break;
            case OVERLAY:
                if (mOverlayBitmap != null)
                    canvas.drawBitmap(mOverlayBitmap, mMatrix, mOverlayPaint);
                break;
            case CONTRAST:
                break;
            case FRAMES:
                if (mFrameBitmap != null)
                    canvas.drawBitmap(mFrameBitmap, mMatrix, mImagePaint);
                break;
            case WARMTH:
                if (mWarmthValue != 0) {
                    if (mImagesList.size() > 0) {
                        canvas.drawBitmap(getAlteredBitmap(), mMatrix, mWarmthPaint);
                    } else {
                        canvas.drawBitmap(mSourceBitmap, mMatrix, mBrightnessPaint);
                    }
                }
        }

        if (mStickersList.size() > 0) {
            drawStickers(canvas);
        }

        if (mTextsList.size() > 0) {
            drawTexts(canvas);
        }

        //canvas.drawBitmap(mSourceBitmap, mMatrix, getAdjustPaint());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(viewWidth, viewHeight);

        mViewWidth = viewWidth - getPaddingLeft() - getPaddingRight();
        mViewHeight = viewHeight - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getBitmap() != null) setupLayout(mViewWidth, mViewHeight);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        mIsInitialized = false;
        super.setImageBitmap(bitmap);
        mSourceBitmap = bitmap;

        updateLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                actionDown(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mIsInSide = false;
                mIsInRotate = false;
                mIsInResize = false;
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                mIsInResize = false;
                mIsInSide = false;
                mIsInRotate = false;
                switch (mCommand) {
                    case NONE:
                        mIsShowOriginalImage = false;
                        invalidate();
                        break;
                    case DRAWING:
                        drawingStop();
                        break;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }

        invalidate();

        return true;
    }

    public void setCommand(EditorCommand command) {
        mCommand = command;
    }

    private void actionDown(MotionEvent event) {
        switch (mCommand) {
            case NONE:
                mIsShowOriginalImage = true;
                invalidate();
                break;
            case STICKERS:
                findCheckedSticker(event);
                break;
            case TEXT:
                findCheckedText(event);
                break;
            case DRAWING:
                drawingStart(event);
                break;
        }
    }

    private void actionMove(MotionEvent event) {
        switch (mCommand) {
            case STICKERS:
                if (mCurrentEditorSticker != null) {
                    if (mIsInResize) {
                        float stickerScale = diagonalLength(event, mCurrentEditorSticker.getPoint()) / mCurrentEditorSticker.getLength();
                        mCurrentEditorSticker.getMatrix().postScale(stickerScale, stickerScale, mCurrentEditorSticker.getPoint().x, mCurrentEditorSticker.getPoint().y);

                        invalidate();
                    } else if (mIsInSide) {
                        moveSticker(event);
                    } else if (mIsInRotate) {
                        Matrix matrix = mCurrentEditorSticker.getMatrix();
                        mCurrentEditorSticker.getMatrix().postRotate((rotationToStartPoint(event, matrix) - mCurrentEditorSticker.getRotateDegree()) * 2, mCurrentEditorSticker.getPoint().x, mCurrentEditorSticker.getPoint().y);
                        mCurrentEditorSticker.setRotateDegree(rotationToStartPoint(event, matrix));
                    }
                }
                break;
            case TEXT:
                if (mCurrentEditorText != null) {
                    if (mIsInSide) {
                        float distanceX = event.getX() - mLastX;
                        float distanceY = event.getY() - mLastY;

                        int newX = mCurrentEditorText.getX() + (int) distanceX;
                        int newY = mCurrentEditorText.getY() + (int) distanceY;

                        mCurrentEditorText.setX(newX);
                        mCurrentEditorText.setY(newY);

                        mLastX = event.getX();
                        mLastY = event.getY();

                        invalidate();
                    }
                }
                break;
            case DRAWING:
                if (isBrushInsideImageHorizontal(event.getX(0))
                        && isBrushInsideImageVertical(event.getY(0)))
                    drawingMove(event);
                break;
        }

    }

    public Bitmap getAlteredBitmap() {
        if (!mImagesList.isEmpty())
            return mImagesList.get(mImagesList.size() - 1).getBitmap();
        return null;
    }

    private void drawingStart(MotionEvent event) {
        mDrawingPath.reset();
        mOriginalDrawingPath.reset();
        mDrawingPath.moveTo(event.getX(), event.getY());
        mOriginalDrawingPath.moveTo(event.getX() * mScale, event.getY() * mScale);
        mLastX = event.getX();
        mLastY = event.getY();

        invalidate();
    }

    private void drawingMove(MotionEvent event) {
        float dX = Math.abs(event.getX() - mLastX);
        float dY = Math.abs(event.getY() - mLastY);

        if (dX >= Drawing.TOUCH_TOLERANCE || dY >= Drawing.TOUCH_TOLERANCE) {
            mDrawingPath.quadTo(mLastX, mLastY,
                    (event.getX() + mLastX) / 2,
                    (event.getY(0) + mLastY) / 2);

            mOriginalDrawingPath.quadTo(mLastX * mScale, mLastY * mScale,
                    ((event.getX() + mLastX) / 2) * mScale,
                    ((event.getY(0) + mLastY) / 2) * mScale);

            mLastX = event.getX();
            mLastY = event.getY();

            mDrawingCirclePath.reset();
            mDrawingCirclePath.addCircle(mLastX, mLastY, 30, Path.Direction.CW);
        }

        invalidate();
    }

    private void drawingStop() {
        mDrawingPath.lineTo(mLastX, mLastY);
        mOriginalDrawingPath.lineTo(mLastX * mScale, mLastY * mScale);
        mDrawingList.add(new Drawing(new Paint(mDrawingPaint),
                new Path(mDrawingPath),
                new Path(mOriginalDrawingPath)));
        mDrawingPath.reset();
        mOriginalDrawingPath.reset();

        invalidate();
    }

    public void setBrushColor(@ColorRes int color) {
        mDrawingPaint.setColor(getResources().getColor(color));
    }

    public void setUndoListener(IUndoListener undoListener) {
        mUndoListener = undoListener;
    }

    public void apply(EditorCommand command) {
        new ImageProcessingTask().execute(command);
    }

    public void setBrushSize(float brushSize) {
        mBrushSize = brushSize; //TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, brushSize, mDisplayMetrics);

        mDrawingPaint.setStrokeWidth(mBrushSize);
    }

    private void moveSticker(MotionEvent event) {
        //if (isStickerInsideImageHorizontal(event.getX(0), mCurrentEditorSticker) && isStickerInsideImageVertical(event.getY(0), mCurrentEditorSticker)) {
        mCurrentEditorSticker.getMatrix().postTranslate(event.getX(0) - mLastX, event.getY(0) - mLastY);

        mLastX = event.getX(0);
        mLastY = event.getY(0);
        invalidate();
        //}
    }

    private float rotationToStartPoint(MotionEvent event, Matrix matrix) {
        float[] arrayOfFloat = new float[9];
        matrix.getValues(arrayOfFloat);
        float x = 0.0f * arrayOfFloat[0] + 0.0f * arrayOfFloat[1] + arrayOfFloat[2];
        float y = 0.0f * arrayOfFloat[3] + 0.0f * arrayOfFloat[4] + arrayOfFloat[5];
        double arc = Math.atan2(event.getY(0) - y, event.getX(0) - x);
        return (float) Math.toDegrees(arc);
    }

    private void midPointToStartPoint(MotionEvent event, EditorSticker sticker) {
        float[] arrayOfFloat = new float[9];
        sticker.getMatrix().getValues(arrayOfFloat);
        float f1 = 0.0f * arrayOfFloat[0] + 0.0f * arrayOfFloat[1] + arrayOfFloat[2];
        float f2 = 0.0f * arrayOfFloat[3] + 0.0f * arrayOfFloat[4] + arrayOfFloat[5];
        float f3 = f1 + event.getX(0);
        float f4 = f2 + event.getY(0);
        sticker.getPoint().set(f3 / 2, f4 / 2);
    }

    private float distance(float x0, float x1, float y0, float y1) {

        float x = x0 - x1;
        float y = y0 - y1;
        return (float) Math.sqrt(x * x + y * y);
    }

    private float dispDistance() {

        return (float) Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
    }

    public void addSticker(Sticker sticker) {
        sticker.setBitmap(((BitmapDrawable) mContext.getResources().getDrawable(sticker.getImage())).getBitmap());

        EditorSticker editorSticker = new EditorSticker(sticker, mEditorFrame);
        editorSticker.setInEdit(true);

        mStickersList.add(editorSticker);

        invalidate();
    }

    private boolean isInResize(MotionEvent event, Rect resizeHandleRect) {
        int left = -20 + resizeHandleRect.left;
        int top = -20 + resizeHandleRect.top;
        int right = 20 + resizeHandleRect.right;
        int bottom = 20 + resizeHandleRect.bottom;
        return event.getX(0) >= left && event.getX(0) <= right && event.getY(0) >= top && event.getY(0) <= bottom;
    }

    private void midDiagonalPoint(PointF pointF, EditorSticker editorSticker) {
        float[] arrayOfFloat = new float[9];
        editorSticker.getMatrix().getValues(arrayOfFloat);
        float f1 = 0.0F * arrayOfFloat[0] + 0.0F * arrayOfFloat[1] + arrayOfFloat[2];
        float f2 = 0.0F * arrayOfFloat[3] + 0.0F * arrayOfFloat[4] + arrayOfFloat[5];
        float f3 = arrayOfFloat[0] * editorSticker.getBitmap().getWidth() + arrayOfFloat[1] * editorSticker.getBitmap().getHeight() + arrayOfFloat[2];
        float f4 = arrayOfFloat[3] * editorSticker.getBitmap().getWidth() + arrayOfFloat[4] * editorSticker.getBitmap().getHeight() + arrayOfFloat[5];
        float f5 = f1 + f3;
        float f6 = f2 + f4;
        pointF.set(f5 / 2.0F, f6 / 2.0F);
    }

    private void drawStickers(Canvas canvas) {
        for (EditorSticker sticker : mStickersList) {
            sticker.drawSticker(canvas);
        }
    }

    private boolean isInButton(MotionEvent event, Rect rect) {
        int left = rect.left;
        int right = rect.right;
        int top = rect.top;
        int bottom = rect.bottom;
        return event.getX(0) >= left && event.getX(0) <= right && event.getY(0) >= top && event.getY(0) <= bottom;
    }

    private boolean isInStickerBitmap(MotionEvent event, EditorSticker sticker) {
        float[] arrayOfFloat1 = new float[9];
        sticker.getMatrix().getValues(arrayOfFloat1);

        float f1 = 0.0F * arrayOfFloat1[0] + 0.0F * arrayOfFloat1[1] + arrayOfFloat1[2];
        float f2 = 0.0F * arrayOfFloat1[3] + 0.0F * arrayOfFloat1[4] + arrayOfFloat1[5];

        float f3 = arrayOfFloat1[0] * sticker.getBitmap().getWidth() + 0.0F * arrayOfFloat1[1] + arrayOfFloat1[2];
        float f4 = arrayOfFloat1[3] * sticker.getBitmap().getWidth() + 0.0F * arrayOfFloat1[4] + arrayOfFloat1[5];

        float f5 = 0.0F * arrayOfFloat1[0] + arrayOfFloat1[1] * sticker.getBitmap().getHeight() + arrayOfFloat1[2];
        float f6 = 0.0F * arrayOfFloat1[3] + arrayOfFloat1[4] * sticker.getBitmap().getHeight() + arrayOfFloat1[5];

        float f7 = arrayOfFloat1[0] * mStickersList.get(0).getBitmap().getWidth() + arrayOfFloat1[1] * sticker.getBitmap().getHeight() + arrayOfFloat1[2];
        float f8 = arrayOfFloat1[3] * mStickersList.get(0).getBitmap().getWidth() + arrayOfFloat1[4] * sticker.getBitmap().getHeight() + arrayOfFloat1[5];

        float[] arrayOfFloat2 = new float[4];
        float[] arrayOfFloat3 = new float[4];

        arrayOfFloat2[0] = f1;
        arrayOfFloat2[1] = f3;
        arrayOfFloat2[2] = f7;
        arrayOfFloat2[3] = f5;

        arrayOfFloat3[0] = f2;
        arrayOfFloat3[1] = f4;
        arrayOfFloat3[2] = f8;
        arrayOfFloat3[3] = f6;
        return pointInRect(arrayOfFloat2, arrayOfFloat3, event.getX(0), event.getY(0));
    }

    private boolean pointInRect(float[] xRange, float[] yRange, float x, float y) {

        double a1 = Math.hypot(xRange[0] - xRange[1], yRange[0] - yRange[1]);
        double a2 = Math.hypot(xRange[1] - xRange[2], yRange[1] - yRange[2]);
        double a3 = Math.hypot(xRange[3] - xRange[2], yRange[3] - yRange[2]);
        double a4 = Math.hypot(xRange[0] - xRange[3], yRange[0] - yRange[3]);

        double b1 = Math.hypot(x - xRange[0], y - yRange[0]);
        double b2 = Math.hypot(x - xRange[1], y - yRange[1]);
        double b3 = Math.hypot(x - xRange[2], y - yRange[2]);
        double b4 = Math.hypot(x - xRange[3], y - yRange[3]);

        double u1 = (a1 + b1 + b2) / 2;
        double u2 = (a2 + b2 + b3) / 2;
        double u3 = (a3 + b3 + b4) / 2;
        double u4 = (a4 + b4 + b1) / 2;


        double s = a1 * a2;

        double ss = Math.sqrt(u1 * (u1 - a1) * (u1 - b1) * (u1 - b2))
                + Math.sqrt(u2 * (u2 - a2) * (u2 - b2) * (u2 - b3))
                + Math.sqrt(u3 * (u3 - a3) * (u3 - b3) * (u3 - b4))
                + Math.sqrt(u4 * (u4 - a4) * (u4 - b4) * (u4 - b1));
        return Math.abs(s - ss) < 0.5;


    }

    public void addText(String text, Typeface typeface, int color, int opacity) {
        mTextsList.add(new EditorText(text, typeface, color, opacity, mEditorFrame));
        invalidate();
    }

    private void drawTexts(Canvas canvas) {
        for (EditorText text : mTextsList) {
            text.drawText(canvas);
        }
    }

    public void setFilter(ColorMatrix colorMatrix) {
        mFilterPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public void setFrame(@Nullable Drawable drawable) {
        if (drawable != null) {
            mFrameBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) drawable).getBitmap(),
                    mSourceBitmap.getWidth(), mSourceBitmap.getHeight(), false);
        } else {
            mFrameBitmap = null;
        }
    }

    public void setOverlay(@DrawableRes int drawableRes) {
        new BitmapScaleTask(mContext, this, R.string.prepare_overlay,
                mSourceBitmap.getWidth(),
                mSourceBitmap.getHeight())
                .execute(drawableRes);
    }

    protected void setupOverlay(Bitmap bitmap) {
        mOverlayBitmap = bitmap;

        BitmapUtil.logBitmapInfo("Overlay", mOverlayBitmap);

        mOverlayPaint.setAlpha(125);

        invalidate();
    }

    public void setOverlayOpacity(int value) {
        int alpha = (int) Math.round(value * 1.5);
        mOverlayPaint.setAlpha(alpha);
        invalidate();
    }

    public void setFilterIntensity(int intensity) {
        int filterIntensity = (int) (intensity * 2.55);
        mFilterPaint.setAlpha(filterIntensity);
        invalidate();
    }

    public void setBrightnessValue(float brightnessValue) {
        if (brightnessValue != 0) {
            mBrightnessValue = brightnessValue;

            mBrightnessPaint.setColorFilter(getBrightnessColorMatrix(mBrightnessValue));

            invalidate();
        }

    }

    public void undo() {
        mImagesList.remove(mImagesList.size() - 1);
        mUndoListener.hasChanged(mImagesList.size());
        invalidate();
    }

    public void setContrastValue() {

    }

    public void setWarmthValue(float warmthValue) {
        if (warmthValue != 0) {
            mWarmthValue = warmthValue;

            mWarmthPaint.setColorFilter(getWarmthColorMatrix(mWarmthValue));

            invalidate();
        }
    }

    private ColorMatrixColorFilter getWarmthColorMatrix(float value) {
        float temp = value / 220;

        return new ColorMatrixColorFilter(new float[]{
                1, 0, 0, temp, 0,
                0, 1, 0, temp / 2, 0,
                0, 0, 1, temp / 4, 0,
                0, 0, 0, 1, 0});
    }

    private ColorMatrixColorFilter getBrightnessColorMatrix(float value) {
        return new ColorMatrixColorFilter(new float[]{
                1, 0, 0, 0, value,
                0, 1, 0, 0, value,
                0, 0, 1, 0, value,
                0, 0, 0, 1, 0});
    }

    private void findCheckedText(MotionEvent event) {
        for (int i = mTextsList.size() - 1; i >= 0; i--) {
            EditorText editorText = mTextsList.get(i);

            if (editorText.getFrameRect().contains(event.getX(), event.getY())) {
                mCurrentEditorText = editorText;
                mIsInSide = true;

                mLastX = event.getX();
                mLastY = event.getY();

                return;
            } else if (editorText.getDeleteHandleDstRect().contains(event.getX(), event.getY())) {
                mCurrentEditorText = null;
                mTextsList.remove(i);
                invalidate();
                return;
            } else if (editorText.getRotateHandleDstRect().contains(event.getX(), event.getY())) {
                mCurrentEditorText = editorText;
                mIsInRotate = true;
                return;
            }
        }
        mCurrentEditorText = null;
    }

    private void findCheckedSticker(MotionEvent event) {
        for (int i = mStickersList.size() - 1; i >= 0; i--) {
            EditorSticker editorSticker = mStickersList.get(i);

            if (isInStickerBitmap(event, editorSticker)) {
                mIsInSide = true;
                mCurrentEditorSticker = editorSticker;

                mLastX = event.getX(0);
                mLastY = event.getY(0);
                return;
            } else if (editorSticker.isInEdit()) {
                if (isInButton(event, editorSticker.getDeleteHandleRect())) {
                    mCurrentEditorSticker = null;
                    mStickersList.remove(i);
                    invalidate();
                    return;
                } else if (isInButton(event, editorSticker.getRotateHandleRect())) {
                    mIsInRotate = true;
                    mCurrentEditorSticker = null;
                    return;
                } else if (isInButton(event, editorSticker.getResizeHandleRect())) {
                    mIsInResize = true;
                    midPointToStartPoint(event, editorSticker);
                    editorSticker.setLength(diagonalLength(event, editorSticker.getPoint()));
                    return;
                }
            }

        }
        mCurrentEditorSticker = null;
    }

    private float diagonalLength(MotionEvent event, PointF pointF) {
        return (float) Math.hypot(event.getX(0) - pointF.x, event.getY(0) - pointF.y);
    }

    private void setMatrix() {
        mMatrix.reset();
        mMatrix.setTranslate(mCenter.x - mImgWidth * 0.5f, mCenter.y - mImgHeight * 0.5f);
        mMatrix.postScale(mScale, mScale, mCenter.x, mCenter.y);
        mMatrix.postRotate(mAngle, mCenter.x, mCenter.y);
    }

    private void setupLayout(int viewW, int viewH) {
        if (viewW == 0 || viewH == 0) return;
        setCenter(new PointF(getPaddingLeft() + viewW * 0.5f, getPaddingTop() + viewH * 0.5f));
        setScale(calcScale(viewW, viewH, mAngle));
        setMatrix();
        mImageRect = calcImageRect(new RectF(0f, 0f, mImgWidth, mImgHeight), mMatrix);
        mIsInitialized = true;
        invalidate();
    }

    private float calcScale(int viewW, int viewH, float angle) {
        mImgWidth = getBitmap().getWidth();
        mImgHeight = getBitmap().getHeight();
        if (mImgWidth <= 0) mImgWidth = viewW;
        if (mImgHeight <= 0) mImgHeight = viewH;
        float viewRatio = (float) viewW / (float) viewH;
        float imgRatio = getRotatedWidth(angle) / getRotatedHeight(angle);
        float scale = 1.0f;
        if (imgRatio >= viewRatio) {
            scale = viewW / getRotatedWidth(angle);
        } else if (imgRatio < viewRatio) {
            scale = viewH / getRotatedHeight(angle);
        }

        mScale = scale;

        return scale;
    }

    private RectF calcImageRect(RectF rect, Matrix matrix) {
        RectF applied = new RectF();
        matrix.mapRect(applied, rect);
        return applied;
    }

    private boolean isBrushInsideImageHorizontal(float x) {
        return mImageRect.left + mBrushSize <= x && mImageRect.right + mBrushSize >= x;
    }

    private boolean isBrushInsideImageVertical(float y) {
        return mImageRect.top + mBrushSize <= y && mImageRect.bottom + mBrushSize >= y;
    }

    private boolean isStickerInsideImageHorizontal(float x, EditorSticker sticker) {
        return (mImageRect.left + sticker.getStickerWight()) <= x
                && (mImageRect.right + sticker.getStickerWight()) >= x;
    }

    private boolean isStickerInsideImageVertical(float y, EditorSticker sticker) {
        return (mImageRect.top + (sticker.getStickerHeight() / 2)) <= y
                && (mImageRect.bottom + (sticker.getStickerHeight() / 2)) >= y;
    }

    private float getDensity() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    private Bitmap getBitmap() {
        Bitmap bm = null;
        Drawable d = getDrawable();
        if (d != null && d instanceof BitmapDrawable) bm = ((BitmapDrawable) d).getBitmap();
        return bm;
    }

    private float getRotatedWidth(float angle) {
        return getRotatedWidth(angle, mImgWidth, mImgHeight);
    }

    private float getRotatedWidth(float angle, float width, float height) {
        return angle % 180 == 0 ? width : height;
    }

    private float getRotatedHeight(float angle) {
        return getRotatedHeight(angle, mImgWidth, mImgHeight);
    }

    private float getRotatedHeight(float angle, float width, float height) {
        return angle % 180 == 0 ? height : width;
    }

    public Bitmap getImageBitmap() {
        return getBitmap();
    }

    private void updateLayout() {
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            setupLayout(mViewWidth, mViewHeight);
        }
    }

    private void setScale(float scale) {
        mScale = scale;
    }

    private void setCenter(PointF center) {
        mCenter = center;
    }

    public void makeImage(Intent intent) {
        new MakeImage(intent).execute();
    }

    private enum TouchArea {
        OUT_OF_BOUNDS, CENTER, LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM
    }

    private class ImageProcessingTask extends AsyncTask<EditorCommand, Void, Bitmap> {
        private Bitmap mBitmap;
        private Canvas mCanvas = new Canvas();
        //private RedrawImagesTask mRedrawImagesTaskTask = new RedrawImagesTask();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();

            if (mImagesList.size() > 0)
                mBitmap = getAlteredBitmap().copy(getAlteredBitmap().getConfig(), true);
            else
                mBitmap = mSourceBitmap.copy(mSourceBitmap.getConfig(), true);
        }

        @Override
        protected Bitmap doInBackground(EditorCommand... editorCommands) {
            mCanvas.setBitmap(mBitmap);
            
            switch (editorCommands[0]) {
                case NONE:
                    break;
                case FILTERS:
                    mCanvas.drawBitmap(mBitmap, 0, 0, mFilterPaint);
                    break;
                case ADJUST:
                    break;
                case OVERLAY:
                    mCanvas.drawBitmap(mOverlayBitmap, 0, 0, mOverlayPaint);
                    break;
                case BRIGHTNESS:
                    mCanvas.drawBitmap(mBitmap, 0, 0, mBrightnessPaint);
                    break;
                case CONTRAST:
                    mCanvas.drawBitmap(mBitmap, 0, 0, mContrastPaint);
                    break;
                case STICKERS:
                    break;
                case FRAMES:
                    if (mFrameBitmap != null)
                        mCanvas.drawBitmap(mBitmap, 0, 0, mImagePaint);
                    break;
                case TEXT:
                    break;
                case DRAWING:
                    break;
                case TILT_SHIFT:
                    break;
                case VIGNETTE:
                    break;
                case SATURATION:
                    break;
                case WARMTH:
                    break;
            }

            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImagesList.add(new EditorImage(mCommand, bitmap));

            mUndoListener.hasChanged(mImagesList.size());

            invalidate();
            mProgressDialog.dismiss();
        }
    }

    /*private class RedrawImagesTask extends AsyncTask<Integer, Void, Bitmap> {
        private Bitmap mBitmap;
        private Canvas mCanvas = new Canvas();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mBitmap = mSourceBitmap.copy(mSourceBitmap.getConfig(), true);
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            mCanvas.setBitmap(mSourceBitmap);
            for (EditorImage editorImage : mImagesList) {
                switch (editorImage.getCommand()) {
                    case R.string.filters:
                        mCanvas.drawBitmap(mBitmap, 0, 0, mFilterPaint);
                        break;
                    case R.string.overlay:
                        break;
                }
            }
            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

        }
    }*/

    private class MakeImage extends AsyncTask<Void, Void, Bitmap> {
        private Canvas mCanvas;
        private Bitmap mBitmap;
        private Intent mIntent;

        private MakeImage(Intent intent) {
            mIntent = intent;
        }

        private void drawStickers(Canvas canvas) {
            for (EditorSticker editorSticker : mStickersList) {
                editorSticker.drawSticker(canvas);
            }
        }

        private void drawTexts(Canvas canvas) {
            for (EditorText editorText : mTextsList) {
                editorText.drawText(canvas);
            }
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            mBitmap = mSourceBitmap.copy(mSourceBitmap.getConfig(), true);
            mCanvas = new Canvas(mBitmap);

            if (mOverlayBitmap != null)
                mCanvas.drawBitmap(mOverlayBitmap, 0, 0, mOverlayPaint);

            if (mFrameBitmap != null)
                mCanvas.drawBitmap(mFrameBitmap, 0, 0, mImagePaint);

            if (mStickersList.size() > 0) {
                drawStickers(mCanvas);
            }

            if (mTextsList.size() > 0) {
                drawTexts(mCanvas);
            }

            if (mDrawingList.size() > 0) {
                for (Drawing drawing : mDrawingList) {
                    mCanvas.drawPath(drawing.getOriginalPath(), drawing.getPaint());
                }
            }

            if (!mDrawingPath.isEmpty()) {
                mCanvas.drawPath(mDrawingPath, mDrawingPaint);
            }

            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            DataHolder.getInstance().setShareBitmap(bitmap);
            mContext.startActivity(mIntent);
        }
    }
}
