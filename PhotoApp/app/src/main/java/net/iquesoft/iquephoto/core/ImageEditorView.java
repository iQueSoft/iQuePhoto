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
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.model.Sticker;

import java.util.ArrayList;
import java.util.List;

public class ImageEditorView extends ImageView {

    private float mBrushSize;

    private boolean mIsInitialized;
    private boolean mHasFilter;
    private boolean mIsDrawingActivated;
    private boolean mIsTextActivated;
    private boolean mIsStickersActivated;

    private boolean mIsInResize;
    private boolean mIsInSide;
    private boolean mIsInRotate;

    private Bitmap mSourceBitmap;
    private Bitmap mAlteredBitmap;

    private Bitmap mOverlayBitmap;
    private Bitmap mFrameBitmap;

    private EditorFrame mEditorFrame;

    private int mCheckedTextId = -1;
    private int mCheckedStickerId = -1;

    private Context mContext;

    private Paint mImagePaint;
    private Paint mFilterPaint;
    private Paint mOverlayPaint;
    private Paint mDrawingPaint;
    private Paint mDrawingCirclePaint;

    private Path mDrawingPath;
    private Path mOriginalDrawingPath;
    private Path mDrawingCirclePath;

    private List<EditorText> mTextsList;
    private List<Drawing> mDrawingList;
    private List<EditorSticker> mStickersList;

    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private float mScale = 1.0f;
    private float mAngle = 0.0f;
    private float mImgWidth = 0.0f;
    private float mImgHeight = 0.0f;

    private float mBrightnessValue = 0;
    private float mWarmthValue = 0;

    private ColorMatrix mAdjustColorMatrix;
    private ColorMatrix mFilterColorMatrix;

    private ColorMatrixColorFilter mAdjustColorMatrixColorFilter;

    private Matrix mMatrix = null;

    private RectF mImageRect;
    private PointF mCenter = new PointF();

    private float mLastX, mLastY;

    private TouchArea mTouchArea = TouchArea.OUT_OF_BOUNDS;

    private int mTouchPadding = 0;

    private boolean mIsEnabled = true;

    public ImageEditorView(Context context) {
        this(context, null);
    }

    public ImageEditorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageEditorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        float density = getDensity();

        mContext = context;

        mTextsList = new ArrayList<>();

        mStickersList = new ArrayList<>();

        mImagePaint = new Paint();
        mFilterPaint = new Paint();
        mOverlayPaint = new Paint();

        mEditorFrame = new EditorFrame(context);

        mAdjustColorMatrix = new ColorMatrix();

        mImagePaint.setFilterBitmap(true);

        mMatrix = new Matrix();

        mScale = 1.0f;

        initDrawing();
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

    @Override
    public void onDraw(Canvas canvas) {
        if (mIsInitialized) {
            setMatrix();
            canvas.drawBitmap(mSourceBitmap, mMatrix, mImagePaint);
        }

        if (mAlteredBitmap != null) {
            canvas.drawBitmap(mAlteredBitmap, mMatrix, mImagePaint);
        } else {
            canvas.drawBitmap(mSourceBitmap, mMatrix, mFilterPaint);
        }

        if (mOverlayBitmap != null)
            canvas.drawBitmap(mOverlayBitmap, mMatrix, mOverlayPaint);

        if (mFrameBitmap != null)
            canvas.drawBitmap(mFrameBitmap, mMatrix, mImagePaint);

        if (mStickersList.size() > 0) {
            drawStickers(canvas);
        }

        if (mTextsList.size() > 0) {
            drawTexts(canvas);
        }

        if (mDrawingList.size() > 0) {
            for (Drawing drawing : mDrawingList) {
                canvas.drawPath(drawing.getPath(), drawing.getPaint());
            }
        }

        if (!mDrawingPath.isEmpty()) {
            canvas.drawPath(mDrawingPath, mDrawingPaint);
        }
        //canvas.drawBitmap(mSourceBitmap, mMatrix, mFilterPaint);

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
        if (getDrawable() != null) setupLayout(mViewWidth, mViewHeight);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mIsStickersActivated) {
                    findCheckedSticker(event);
                } else if (mIsTextActivated) {
                    findCheckedText(event);
                } else if (mIsDrawingActivated) {
                    if (isBrushInsideImageHorizontal(event.getX())
                            && isBrushInsideImageVertical(event.getY()))
                        drawingStart(event);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mIsInSide = false;
                mIsInRotate = false;
                mIsInResize = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsStickersActivated) {
                    if (mCheckedStickerId != -1) {
                        EditorSticker sticker = mStickersList.get(mCheckedStickerId);
                        if (mIsInResize) {
                            float stickerScale = diagonalLength(event, sticker.getPoint()) / sticker.getLength();
                            sticker.getMatrix().postScale(stickerScale, stickerScale, sticker.getPoint().x, sticker.getPoint().y);

                            invalidate();
                        } else if (mIsInSide) {
                            moveSticker(event.getX(0), event.getY(0),
                                    mStickersList.get(mCheckedStickerId));
                        } else if (mIsInRotate) {
                            Matrix matrix = sticker.getMatrix();
                            sticker.getMatrix().postRotate((rotationToStartPoint(event, matrix) - sticker.getRotateDegree()) * 2, sticker.getPoint().x, sticker.getPoint().y);
                            sticker.setRotateDegree(rotationToStartPoint(event, matrix));
                        }
                    }
                } else if (mIsTextActivated) {
                    if (mCheckedTextId != -1) {
                        EditorText editorText = mTextsList.get(mCheckedTextId);
                        if (mIsInSide) {
                            float distanceX = event.getX() - mLastX;
                            float distanceY = event.getY() - mLastY;

                            int newX = editorText.getX() + (int) distanceX;
                            int newY = editorText.getY() + (int) distanceY;

                            editorText.setX(newX);
                            editorText.setY(newY);

                            mLastX = event.getX();
                            mLastY = event.getY();

                            invalidate();
                        }
                    }
                } else if (mIsDrawingActivated) {
                    if (isBrushInsideImageHorizontal(event.getX(0))
                            && isBrushInsideImageVertical(event.getY(0)))
                        drawingMove(event);
                }
                //mTextsList.get(mCheckedTextId).setSize(mTextsList.get(mCheckedTextId).getSize() * scale);
                break;
            case MotionEvent.ACTION_UP:
                mIsInResize = false;
                mIsInSide = false;
                mIsInRotate = false;

                if (mIsDrawingActivated) {
                    if (isBrushInsideImageHorizontal(event.getX(0))
                            && isBrushInsideImageVertical(event.getY(0)))
                        drawingStop();
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }

        invalidate();

        return true;
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

    public void setBrushSize(float brushSize) {
        mBrushSize = brushSize; //TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, brushSize, mDisplayMetrics);

        mDrawingPaint.setStrokeWidth(mBrushSize);
    }

    private void moveSticker(float x, float y, EditorSticker sticker) {
        if (isStickerInsideImageHorizontal(x, sticker) && isStickerInsideImageVertical(y, sticker)) {
            sticker.getMatrix().postTranslate(x - mLastX, y - mLastY);

            mLastX = x;
            mLastY = y;
            invalidate();
        }
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

    private Paint getAdjustPaint() {
        Paint paint = new Paint();

        mAdjustColorMatrix.setConcat(getWarmthColorMatrix(mWarmthValue), getBrightnessColorMatrix(mBrightnessValue));

        paint.setColorFilter(new ColorMatrixColorFilter(mAdjustColorMatrix));

        return paint;
    }

    public void setFilter(@Nullable ColorMatrix colorMatrix) {
        mFilterColorMatrix = colorMatrix;
        if (colorMatrix != null) {
            mHasFilter = true;
            mFilterPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            new FilerImageTask().execute();
        } else {
            mHasFilter = false;
        }
    }

    public void setFrame(@Nullable Drawable drawable) {
        if (drawable != null) {
            mFrameBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) drawable).getBitmap(),
                    mSourceBitmap.getWidth(), mSourceBitmap.getHeight(), false);
        } else {
            mFrameBitmap = null;
        }
    }

    public void setOverlay(@Nullable Drawable drawable) {
        // FIXME: OutMemoryException when work with large image.
        if (drawable != null) {
            mOverlayBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) drawable).getBitmap(),
                    mSourceBitmap.getWidth(), mSourceBitmap.getHeight(), false);
            mOverlayPaint.setAlpha(125);
        } else {
            mOverlayBitmap = null;
        }

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
        mBrightnessValue = brightnessValue;
        invalidate();
    }

    public void setWarmthValue(float warmthValue) {
        mWarmthValue = warmthValue;
        invalidate();
    }

    private ColorMatrix getWarmthColorMatrix(float value) {
        float temp = value / 220;

        return new ColorMatrix(new float[]
                {
                        1, 0, 0, temp, 0,
                        0, 1, 0, temp / 2, 0,
                        0, 0, 1, temp / 4, 0,
                        0, 0, 0, 1, 0
                });
    }

    private ColorMatrix getBrightnessColorMatrix(float value) {
        return new ColorMatrix(new float[]{1, 0, 0, 0, value,
                0, 1, 0, 0, value,
                0, 0, 1, 0, value,
                0, 0, 0, 1, 0});
    }

    private void findCheckedText(MotionEvent event) {
        for (int i = mTextsList.size() - 1; i >= 0; i--) {
            EditorText text = mTextsList.get(i);

            if (text.getFrameRect().contains(event.getX(), event.getY())) {
                mCheckedTextId = i;
                mIsInSide = true;

                mLastX = event.getX();
                mLastY = event.getY();

                return;
            } else if (text.getDeleteHandleDstRect().contains(event.getX(), event.getY())) {
                mTextsList.remove(i);
                mCheckedTextId = -1;
                return;
            } else if (text.getRotateHandleDstRect().contains(event.getX(), event.getY())) {
                mCheckedTextId = i;
                mIsInRotate = true;
            }
        }
        mCheckedTextId = -1;
    }

    private void findCheckedSticker(MotionEvent event) {
        for (int i = mStickersList.size() - 1; i >= 0; i--) {
            EditorSticker sticker = mStickersList.get(i);

            if (isInStickerBitmap(event, sticker)) {
                mIsInSide = true;
                mCheckedStickerId = i;

                mLastX = event.getX(0);
                mLastY = event.getY(0);
                return;
            } else if (sticker.isInEdit()) {
                if (isInButton(event, sticker.getDeleteHandleRect())) {
                    mStickersList.remove(i);
                    mCheckedStickerId = -1;
                    invalidate();
                    return;
                } else if (isInButton(event, sticker.getRotateHandleRect())) {
                    mIsInRotate = true;
                    mCheckedStickerId = i;
                    return;
                } else if (isInButton(event, sticker.getResizeHandleRect())) {
                    mIsInResize = true;
                    midPointToStartPoint(event, sticker);
                    sticker.setLength(diagonalLength(event, sticker.getPoint()));
                    return;
                }
            }

        }
        mCheckedStickerId = -1;
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

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        mIsInitialized = false;
        super.setImageBitmap(bitmap);
        mSourceBitmap = bitmap;

        updateLayout();
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

    public void setStickersActivated(boolean isStickersActivated) {
        mIsStickersActivated = isStickersActivated;

        mIsTextActivated = false;
        mIsDrawingActivated = false;

        if (mStickersList.size() > 0) {
            if (!mIsStickersActivated) {
                for (EditorSticker sticker : mStickersList) {
                    sticker.setInEdit(false);
                }
                invalidate();
            } else {
                for (int i = 0; i < mStickersList.size(); i++) {
                    if (i == (mStickersList.size() - 1)) {
                        mStickersList.get(i).setInEdit(true);
                    } else {
                        mStickersList.get(i).setInEdit(false);
                    }
                }
                invalidate();
            }
        }
    }

    public void setTextActivated(boolean isTextActivated) {
        mIsTextActivated = isTextActivated;

        mIsStickersActivated = false;
        mIsDrawingActivated = false;
    }

    public void setDrawingActivated(boolean isDrawingActivated) {
        mIsDrawingActivated = isDrawingActivated;

        mIsStickersActivated = false;
        mIsTextActivated = false;
    }

    public void makeImage(Intent intent) {
        new MakeImage(intent).execute();
    }

    private enum TouchArea {
        OUT_OF_BOUNDS, CENTER, LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM
    }

    private class FilerImageTask extends AsyncTask<Void, Void, Bitmap> {
        private Canvas mCanvas;
        private Bitmap mBitmap;

        @Override
        protected Bitmap doInBackground(Void... params) {
            mBitmap = mSourceBitmap.copy(mSourceBitmap.getConfig(), true);
            mCanvas = new Canvas(mBitmap);

            mCanvas.drawBitmap(mSourceBitmap, 0, 0, mFilterPaint);

            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mAlteredBitmap = bitmap;

            invalidate();
        }
    }

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
                mCanvas.drawBitmap(mOverlayBitmap, mMatrix, mOverlayPaint);

            if (mFrameBitmap != null)
                mCanvas.drawBitmap(mFrameBitmap, mMatrix, mImagePaint);

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
