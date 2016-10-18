package net.iquesoft.iquephoto.core;

import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Text;

import java.util.LinkedList;
import java.util.List;

public class ImageEditorView extends ImageView {

    private Context mContext;

    private Bitmap mSourceBitmap;

    private Bitmap mOverlayBitmap;
    private Bitmap mFrameBitmap;
    private Paint mOverlayPaint;

    private Drawable mSourceDrawable;

    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private float mScale = 1.0f;
    private float mAngle = 0.0f;
    private float mImgWidth = 0.0f;
    private float mImgHeight = 0.0f;

    private float mBrightnessValue = 0;
    private float mWarmthValue = 0;

    private ColorMatrix mAdjustColorMatrix;
    private ColorMatrixColorFilter mAdjustColorMatrixColorFilter;

    private Paint mFilterPaint;

    private ColorMatrix mFilterColorMatrix;
    private boolean mHasFilter;

    private int mCheckedTextId = -1;
    private List<Text> mTextsList = new LinkedList<Text>();

    private boolean mIsInitialized = false;
    private Matrix mMatrix = null;
    private Paint mPaintTranslucent;
    private Paint mPaintBitmap;
    private RectF mImageRect;
    private PointF mCenter = new PointF();
    private float mLastX, mLastY;
    private LoadCallback mLoadCallback = null;
    private CropCallback mCropCallback = null;
    private SaveCallback mSaveCallback = null;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Uri mSourceUri = null;
    private Uri mSaveUri = null;
    private int mExifRotation = 0;
    private int mOutputMaxWidth;
    private int mOutputMaxHeight;
    private int mOutputWidth = 0;
    private int mOutputHeight = 0;
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.PNG;
    private int mInputImageWidth = 0;
    private int mInputImageHeight = 0;
    private int mOutputImageWidth = 0;
    private int mOutputImageHeight = 0;
    private boolean mIsLoading = false;

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

        mPaintTranslucent = new Paint();
        mPaintBitmap = new Paint();
        mPaintBitmap.setFilterBitmap(true);

        mFilterPaint = new Paint();
        mOverlayPaint = new Paint();

        mAdjustColorMatrix = new ColorMatrix();

        mMatrix = new Matrix();
        mScale = 1.0f;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mIsInitialized) {
            setMatrix();

            canvas.drawBitmap(mSourceBitmap, mMatrix, mPaintBitmap);
        }

        if (mOverlayBitmap != null)
            canvas.drawBitmap(mOverlayBitmap, mMatrix, mOverlayPaint);

        if (mFrameBitmap != null)
            canvas.drawBitmap(mFrameBitmap, mMatrix, mPaintBitmap);
        //canvas.drawBitmap(mSourceBitmap, mMatrix, mFilterPaint);

        //canvas.drawBitmap(mSourceBitmap, mMatrix, getAdjustPaint());
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
            //mFilterPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            mSourceDrawable.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            mSourceBitmap = ((BitmapDrawable) mSourceDrawable).getBitmap();
        } else {
            mHasFilter = false;
        }
        invalidate();
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

    private void findCheckedText(int x, int y) {
        for (int i = mTextsList.size() - 1; i >= 0; i--) {
            if (mTextsList.get(i).getTextArea().contains(x, y)) {
                mCheckedTextId = i;
                Log.d("Text", mTextsList.get(i).getText());

                /*if (deleteTextActivated) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle)
                            *//*.setTitle(context.getString(R.string.permission_denied))*//*
                            .setMessage(mContext.getString(R.string.text_delete_alert))
                            .setPositiveButton(mContext.getString(R.string.yes), (dialogInterface, i2) -> {
                                deleteText(mTextsList.get(mCheckedTextId));
                            })
                            .setNegativeButton(mContext.getString(R.string.no), (dialogInterface, i1) -> {
                                dialogInterface.dismiss();
                            });
                    builder.show();

                } else {
                    return;
                }*/
            }
        }
        mCheckedTextId = -1;
    }

    /*public void addText(Text text) {
        if (text.getSize() == 0) {
            text.setSize(defaultTextSize);
        }
        if (text.getColor() == 0) {
            text.setColor(defaultTextColor);
        }
        if (text.getTypeface() == null) {
            text.setTypeface(textTypeface);
        }
        this.textsList.add(text);
        invalidate();
    }*/

    private void deleteText(Text text) {
        if (mTextsList != null && mTextsList.contains(text)) {
            mTextsList.remove(text);
            invalidate();
            requestLayout();
            String string = String.format(getResources().getString(R.string.text_deleted), text.getText());
            Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
        }
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
        mImgWidth = getDrawable().getIntrinsicWidth();
        mImgHeight = getDrawable().getIntrinsicHeight();
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
        return scale;
    }

    private RectF calcImageRect(RectF rect, Matrix matrix) {
        RectF applied = new RectF();
        matrix.mapRect(applied, rect);
        return applied;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsInitialized) return false;
        if (!mIsEnabled) return false;
        if (mIsLoading) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onDown(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                onMove(event);
                if (mTouchArea != TouchArea.OUT_OF_BOUNDS) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                //onCancel();
                return true;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                //onUp(event);
                return true;
        }
        return false;
    }

    private void onDown(MotionEvent e) {
        invalidate();
        mLastX = e.getX();
        mLastY = e.getY();
        //checkTouchArea(e.getX(), e.getY());
    }

    private void onMove(MotionEvent e) {
        float diffX = e.getX() - mLastX;
        float diffY = e.getY() - mLastY;
        switch (mTouchArea) {
            case CENTER:
                //moveFrame(diffX, diffY);
                break;
            case LEFT_TOP:
                //moveHandleLT(diffX, diffY);
                break;
            case RIGHT_TOP:
                //moveHandleRT(diffX, diffY);
                break;
            case LEFT_BOTTOM:
                // moveHandleLB(diffX, diffY);
                break;
            case RIGHT_BOTTOM:
                //moveHandleRB(diffX, diffY);
                break;
            case OUT_OF_BOUNDS:
                break;
        }
        invalidate();
        mLastX = e.getX();
        mLastY = e.getY();
    }

    private boolean isInsideHorizontal(float x) {
        return mImageRect.left <= x && mImageRect.right >= x;
    }

    private boolean isInsideVertical(float y) {
        return mImageRect.top <= y && mImageRect.bottom >= y;
    }

    // Utility /////////////////////////////////////////////////////////////////////////////////////

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


    /*private Bitmap scaleBitmapIfNeeded(Bitmap cropped) {
        int width = cropped.getWidth();
        int height = cropped.getHeight();
        int outWidth = 0;
        int outHeight = 0;
        float imageRatio = getRatioX(mFrameRect.width()) / getRatioY(mFrameRect.height());

        if (mOutputWidth > 0) {
            outWidth = mOutputWidth;
            outHeight = Math.round(mOutputWidth / imageRatio);
        } else if (mOutputHeight > 0) {
            outHeight = mOutputHeight;
            outWidth = Math.round(mOutputHeight * imageRatio);
        } else {
            if (mOutputMaxWidth > 0 && mOutputMaxHeight > 0
                    && (width > mOutputMaxWidth || height > mOutputMaxHeight)) {
                float maxRatio = (float) mOutputMaxWidth / (float) mOutputMaxHeight;
                if (maxRatio >= imageRatio) {
                    outHeight = mOutputMaxHeight;
                    outWidth = Math.round((float) mOutputMaxHeight * imageRatio);
                } else {
                    outWidth = mOutputMaxWidth;
                    outHeight = Math.round((float) mOutputMaxWidth / imageRatio);
                }
            }
        }

        if (outWidth > 0 && outHeight > 0) {
            Bitmap scaled = Utils.getScaledBitmap(cropped, outWidth, outHeight);
            if (cropped != getBitmap() && cropped != scaled) {
                cropped.recycle();
            }
            cropped = scaled;
        }
        return cropped;
    }*/

    /**
     * Get source image bitmap
     *
     * @return src bitmap
     */
    public Bitmap getImageBitmap() {
        return getBitmap();
    }

    /**
     * Set source image bitmap
     *
     * @param bitmap src image bitmap
     */
    @Override
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        mSourceBitmap = bitmap;
        mSourceDrawable = new BitmapDrawable(bitmap);
    }

    /**
     * Set image drawable.
     *
     * @param drawable source image drawable
     */
    @Override
    public void setImageDrawable(Drawable drawable) {
        mIsInitialized = false;
        super.setImageDrawable(drawable);

        //mSourceBitmap = ((BitmapDrawable) drawable).getBitmap();

        updateLayout();
    }

    /**
     * Set image uri
     *
     * @param uri source image local uri
     */
    @Override
    public void setImageURI(Uri uri) {
        mIsInitialized = false;
        super.setImageURI(uri);
        updateLayout();
    }

    private void updateLayout() {
        resetImageInfo();
        Drawable d = getDrawable();
        if (d != null) {
            setupLayout(mViewWidth, mViewHeight);
        }
    }

    private void resetImageInfo() {
        if (mIsLoading) return;
        mSourceUri = null;
        mSaveUri = null;
        mInputImageWidth = 0;
        mInputImageHeight = 0;
        mOutputImageWidth = 0;
        mOutputImageHeight = 0;
        mAngle = mExifRotation;
    }

    /**
     * Set crop frame handle touch padding(touch area) in density-independent pixels.
     * <p>
     * handle touch area : a circle of radius R.(R = handle size + touch padding)
     *
     * @param paddingDp crop frame handle touch padding(touch area) in density-independent pixels
     */
    public void setTouchPaddingInDp(int paddingDp) {
        mTouchPadding = (int) (paddingDp * getDensity());
    }

    private void setScale(float mScale) {
        this.mScale = mScale;
    }

    private void setCenter(PointF mCenter) {
        this.mCenter = mCenter;
    }

    private enum TouchArea {
        OUT_OF_BOUNDS, CENTER, LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM
    }
}
