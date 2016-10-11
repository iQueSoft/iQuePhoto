package net.iquesoft.iquephoto.core;

import com.isseiaoki.simplecropview.animation.SimpleValueAnimator;
import com.isseiaoki.simplecropview.animation.SimpleValueAnimatorListener;
import com.isseiaoki.simplecropview.animation.ValueAnimatorV14;
import com.isseiaoki.simplecropview.animation.ValueAnimatorV8;
import com.isseiaoki.simplecropview.callback.Callback;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.isseiaoki.simplecropview.util.Logger;
import com.isseiaoki.simplecropview.util.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import net.iquesoft.iquephoto.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageEditorView extends ImageView {

    private static final int TRANSPARENT = 0x00000000;
    private static final int TRANSLUCENT_BLACK = 0xBB000000;

    // Member variables ////////////////////////////////////////////////////////////////////////////

    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private float mScale = 1.0f;
    private float mAngle = 0.0f;
    private float mImgWidth = 0.0f;
    private float mImgHeight = 0.0f;

    private Paint mPaintFilter;
    private boolean mHasFilter;
    private int mFilterIntensity;

    private boolean mIsInitialized = false;
    private Matrix mMatrix = null;
    private Paint mPaintTranslucent;
    private Paint mPaintBitmap;
    private RectF mImageRect;
    private PointF mCenter = new PointF();
    private float mLastX, mLastY;
    private boolean mIsRotating = false;
    private boolean mIsAnimating = false;
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
    private int mBackgroundColor;
    private int mOverlayColor;

    // Constructor /////////////////////////////////////////////////////////////////////////////////

    public ImageEditorView(Context context) {
        this(context, null);
    }

    public ImageEditorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageEditorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        float density = getDensity();

        mPaintTranslucent = new Paint();
        mPaintBitmap = new Paint();
        mPaintBitmap.setFilterBitmap(true);

        mPaintFilter = new Paint();

        mMatrix = new Matrix();
        mScale = 1.0f;
        mBackgroundColor = TRANSPARENT;
        mOverlayColor = TRANSLUCENT_BLACK;
    }

    public void setFilter(@Nullable ColorMatrix colorMatrix) {
        if (colorMatrix != null) {
            mHasFilter = true;
            mPaintFilter.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        } else {
            mHasFilter = false;
        }
        invalidate();
    }

    public void setFilterIntensity(int intensity) {
        mFilterIntensity = (int) (intensity * 2.55);
        mPaintFilter.setAlpha(mFilterIntensity);
        invalidate();
    }

    // Lifecycle methods ///////////////////////////////////////////////////////////////////////////
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.image = getBitmap();
        ss.backgroundColor = this.mBackgroundColor;
        ss.overlayColor = this.mOverlayColor;
        ss.touchPadding = this.mTouchPadding;
        ss.angle = this.mAngle;
        ss.exifRotation = this.mExifRotation;
        ss.sourceUri = this.mSourceUri;
        ss.saveUri = this.mSaveUri;
        ss.compressFormat = this.mCompressFormat;
        ss.outputMaxWidth = this.mOutputMaxWidth;
        ss.outputMaxHeight = this.mOutputMaxHeight;
        ss.outputWidth = this.mOutputWidth;
        ss.outputHeight = this.mOutputHeight;
        ss.inputImageWidth = this.mInputImageWidth;
        ss.inputImageHeight = this.mInputImageHeight;
        ss.outputImageWidth = this.mOutputImageWidth;
        ss.outputImageHeight = this.mOutputImageHeight;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mTouchPadding = ss.touchPadding;
        this.mAngle = ss.angle;
        this.mExifRotation = ss.exifRotation;
        this.mSourceUri = ss.sourceUri;
        this.mSaveUri = ss.saveUri;
        this.mCompressFormat = ss.compressFormat;
        this.mOutputMaxWidth = ss.outputMaxWidth;
        this.mOutputMaxHeight = ss.outputMaxHeight;
        this.mOutputWidth = ss.outputWidth;
        this.mOutputHeight = ss.outputHeight;
        this.mInputImageWidth = ss.inputImageWidth;
        this.mInputImageHeight = ss.inputImageHeight;
        this.mOutputImageWidth = ss.outputImageWidth;
        this.mOutputImageHeight = ss.outputImageHeight;
        setImageBitmap(ss.image);
        requestLayout();
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
    public void onDraw(Canvas canvas) {
        canvas.drawColor(mBackgroundColor);

        if (mIsInitialized) {
            setMatrix();
            Bitmap bm = getBitmap();
            if (bm != null) {
                canvas.drawBitmap(bm, mMatrix, mPaintBitmap);
                // draw edit frame
                //drawCropFrame(canvas);
            }
        }
        if (mHasFilter) {
            Bitmap bm = getBitmap();
            canvas.drawBitmap(bm, mMatrix, mPaintFilter);
        }
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
        if (mIsRotating) return false;
        if (mIsAnimating) return false;
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

    private float sq(float value) {
        return value * value;
    }

    private float constrain(float val, float min, float max, float defaultVal) {
        if (val < min || val > max) return defaultVal;
        return val;
    }

    private void postErrorOnMainThread(final Callback callback) {
        if (callback == null) return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            callback.onError();
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError();
                }
            });
        }
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


    /*private void saveToFile(Bitmap bitmap, final Uri uri) {
        OutputStream outputStream = null;
        try {
            outputStream = getContext().getContentResolver()
                    .openOutputStream(uri);
            if (outputStream != null) {
                bitmap.compress(mCompressFormat, mCompressQuality, outputStream);
            }
        } catch (IOException e) {
            Logger.e("An error occurred while saving the image: " + uri, e);
            postErrorOnMainThread(mSaveCallback);
        } finally {
            Utils.closeQuietly(outputStream);
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mSaveCallback != null) mSaveCallback.onSuccess(uri);
            }
        });
    }*/

    // Public methods //////////////////////////////////////////////////////////////////////////////

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
        super.setImageBitmap(bitmap); // calles setImageDrawable internally
    }

    /**
     * Set source image resource id
     *
     * @param resId source image resource id
     */
    @Override
    public void setImageResource(int resId) {
        mIsInitialized = false;
        super.setImageResource(resId);
        updateLayout();
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
     * Crop image from Uri
     *
     * @param saveUri      Uri for saving the cropped image
     * @param cropCallback Callback for cropping the image
     * @param saveCallback Callback for saving the image
     *//*
    public void startCrop(Uri saveUri, CropCallback cropCallback, SaveCallback saveCallback) {
        mSaveUri = saveUri;
        mCropCallback = cropCallback;
        mSaveCallback = saveCallback;
        if (mIsCropping) {
            postErrorOnMainThread(mCropCallback);
            postErrorOnMainThread(mSaveCallback);
            return;
        }
        mIsCropping = true;
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap cropped;

                // Use thumbnail for crop
                if (mSourceUri == null) {

                }
                // Use file for crop
                else {
                    //cropped = decodeRegion();
                }

                // Success
                if (cropped != null) {
                    //cropped = scaleBitmapIfNeeded(cropped);
                    final Bitmap tmp = cropped;
                    mOutputImageWidth = tmp.getWidth();
                    mOutputImageHeight = tmp.getHeight();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCropCallback != null) mCropCallback.onSuccess(tmp);
                            if (mIsDebug) invalidate();
                        }
                    });
                }
                // Error
                else {
                    postErrorOnMainThread(mCropCallback);
                }

                if (mSaveUri == null) {
                    postErrorOnMainThread(mSaveCallback);
                    return;
                }
                saveToFile(cropped, mSaveUri);
                mIsCropping = false;
            }
        });
    }*/


    /**
     * Set image overlay color
     *
     * @param overlayColor color resId or color int(ex. 0xFFFFFFFF)
     */
    public void setOverlayColor(int overlayColor) {
        this.mOverlayColor = overlayColor;
        invalidate();
    }

    /**
     * Set view background color
     *
     * @param bgColor color resId or color int(ex. 0xFFFFFFFF)
     */
    public void setBackgroundColor(int bgColor) {
        this.mBackgroundColor = bgColor;
        invalidate();
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

    /**
     * Set locking the crop frame.
     *
     * @param enabled should lock crop frame?
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mIsEnabled = enabled;
    }

    /**
     * Set Image Load callback
     *
     * @param callback callback
     */
    public void setLoadCallback(LoadCallback callback) {
        mLoadCallback = callback;
    }

    /**
     * Set Image Crop callback
     *
     * @param callback callback
     */
    public void setCropCallback(CropCallback callback) {
        mCropCallback = callback;
    }

    /**
     * Set Image Save callback
     *
     * @param callback callback
     */
    public void setSaveCallback(SaveCallback callback) {
        mSaveCallback = callback;
    }

    /**
     * Set fixed width for output
     * (After cropping, the image is scaled to the specified size.)
     *
     * @param outputWidth output width
     */
    public void setOutputWidth(int outputWidth) {
        mOutputWidth = outputWidth;
        mOutputHeight = 0;
    }

    /**
     * Set fixed height for output
     * (After cropping, the image is scaled to the specified size.)
     *
     * @param outputHeight output height
     */
    public void setOutputHeight(int outputHeight) {
        mOutputHeight = outputHeight;
        mOutputWidth = 0;
    }

    /**
     * Set maximum size for output
     * (If cropped image size is larger than max size, the image is scaled to the smaller size.
     * If fixed output width/height has already set, these parameters are ignored.)
     *
     * @param maxWidth  max output width
     * @param maxHeight max output height
     */
    public void setOutputMaxSize(int maxWidth, int maxHeight) {
        mOutputMaxWidth = maxWidth;
        mOutputMaxHeight = maxHeight;
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

    // Save/Restore support ////////////////////////////////////////////////////////////////////////

    public static class SavedState extends BaseSavedState {
        Bitmap image;
        int backgroundColor;
        int overlayColor;
        int frameColor;
        boolean showGuide;
        boolean showHandle;
        int handleSize;
        int touchPadding;
        float minFrameSize;
        float customRatioX;
        float customRatioY;
        float frameStrokeWeight;
        float guideStrokeWeight;
        boolean isCropEnabled;
        int handleColor;
        int guideColor;
        float initialFrameScale;
        float angle;
        boolean isAnimationEnabled;
        int animationDuration;
        int exifRotation;
        Uri sourceUri;
        Uri saveUri;
        Bitmap.CompressFormat compressFormat;
        int compressQuality;
        boolean isDebug;
        int outputMaxWidth;
        int outputMaxHeight;
        int outputWidth;
        int outputHeight;
        boolean isHandleShadowEnabled;
        int inputImageWidth;
        int inputImageHeight;
        int outputImageWidth;
        int outputImageHeight;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            image = in.readParcelable(Bitmap.class.getClassLoader());
            backgroundColor = in.readInt();
            overlayColor = in.readInt();
            frameColor = in.readInt();
            showGuide = (in.readInt() != 0);
            showHandle = (in.readInt() != 0);
            handleSize = in.readInt();
            touchPadding = in.readInt();
            minFrameSize = in.readFloat();
            customRatioX = in.readFloat();
            customRatioY = in.readFloat();
            frameStrokeWeight = in.readFloat();
            guideStrokeWeight = in.readFloat();
            isCropEnabled = (in.readInt() != 0);
            handleColor = in.readInt();
            guideColor = in.readInt();
            initialFrameScale = in.readFloat();
            angle = in.readFloat();
            isAnimationEnabled = (in.readInt() != 0);
            animationDuration = in.readInt();
            exifRotation = in.readInt();
            sourceUri = in.readParcelable(Uri.class.getClassLoader());
            saveUri = in.readParcelable(Uri.class.getClassLoader());
            compressFormat = (Bitmap.CompressFormat) in.readSerializable();
            compressQuality = in.readInt();
            isDebug = (in.readInt() != 0);
            outputMaxWidth = in.readInt();
            outputMaxHeight = in.readInt();
            outputWidth = in.readInt();
            outputHeight = in.readInt();
            isHandleShadowEnabled = (in.readInt() != 0);
            inputImageWidth = in.readInt();
            inputImageHeight = in.readInt();
            outputImageWidth = in.readInt();
            outputImageHeight = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flag) {
            super.writeToParcel(out, flag);
            out.writeParcelable(image, flag);
            out.writeInt(backgroundColor);
            out.writeInt(overlayColor);
            out.writeInt(frameColor);
            out.writeInt(showGuide ? 1 : 0);
            out.writeInt(showHandle ? 1 : 0);
            out.writeInt(handleSize);
            out.writeInt(touchPadding);
            out.writeFloat(minFrameSize);
            out.writeFloat(customRatioX);
            out.writeFloat(customRatioY);
            out.writeFloat(frameStrokeWeight);
            out.writeFloat(guideStrokeWeight);
            out.writeInt(isCropEnabled ? 1 : 0);
            out.writeInt(handleColor);
            out.writeInt(guideColor);
            out.writeFloat(initialFrameScale);
            out.writeFloat(angle);
            out.writeInt(isAnimationEnabled ? 1 : 0);
            out.writeInt(animationDuration);
            out.writeInt(exifRotation);
            out.writeParcelable(sourceUri, flag);
            out.writeParcelable(saveUri, flag);
            out.writeSerializable(compressFormat);
            out.writeInt(compressQuality);
            out.writeInt(isDebug ? 1 : 0);
            out.writeInt(outputMaxWidth);
            out.writeInt(outputMaxHeight);
            out.writeInt(outputWidth);
            out.writeInt(outputHeight);
            out.writeInt(isHandleShadowEnabled ? 1 : 0);
            out.writeInt(inputImageWidth);
            out.writeInt(inputImageHeight);
            out.writeInt(outputImageWidth);
            out.writeInt(outputImageHeight);
        }

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public SavedState createFromParcel(final Parcel inParcel) {
                return new SavedState(inParcel);
            }

            public SavedState[] newArray(final int inSize) {
                return new SavedState[inSize];
            }
        };
    }
}
