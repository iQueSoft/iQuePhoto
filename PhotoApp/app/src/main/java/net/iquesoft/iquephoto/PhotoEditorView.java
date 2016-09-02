package net.iquesoft.iquephoto;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import net.iquesoft.iquephoto.model.Sticker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PhotoEditorView extends View implements View.OnTouchListener {

    private final String TAG = PhotoEditorView.class.getSimpleName();

    private static final int LONG_PRESS_MILLISECOND = 1000;
    private static final long LONG_PRESS_TEXT_MILLISECOND = 2000;

    private List<Bitmap> imageList = new ArrayList<>();

    private List<PhotoEditorText> textsList = new LinkedList<PhotoEditorText>();
    private List<Sticker> stickersList = new LinkedList<Sticker>();

    // display width height.
    // double tap for determining
    private long mLastTime = 0;
    private boolean isDoubleTap;
    private int mDoubleTapX;
    private int mDoubleTapY;
    private float mPrevDistance;
    private boolean isScaling;
    private int mPrevMoveX;
    private int mPrevMoveY;
    private float fingersAngle = 0;
    private double mRotateCenterDistance = 0;
    private double mRotateCenterAngle = 0;
    private float mIndependentScale = 1f;
    private float mIndependentAngle;
    private PhotoEditorImage photoEditorImage;
    private int emptyColor;
    private float defaultTextSize = 14f;
    private int defaultTextColor;
    private Typeface textTypeface;

    private Rect imageSquare;
    private Rect allSquare;
    private int imagePadding;
    private Rect imagePart;
    private int imagePaddingColor = Color.BLACK;
    private boolean freeTransform;
    private int checkedTextId = -1;
    private Bitmap emptryBackgroung = null;
    private OnSquareEditorListener squareEditorListener;
    private String emptyText;
    private boolean isSaveInProccess;
    private float scalingForSave = 1;
    private boolean calculatePadding = true;
    private boolean longClick;
    private boolean changeImage;
    private OnSquareEditorPictureClickListener onSquareEditorPictureClickListener;
    private boolean imageCentering;
    private boolean drawTextBorder;

    public PhotoEditorView(Context context) {
        super(context);
    }

    public PhotoEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GiantSquareEditorView, 0, 0);
        defaultTextColor = a.getColor(R.styleable.GiantSquareEditorView_defaultTextColor, Color.DKGRAY);
        emptyColor = a.getColor(R.styleable.GiantSquareEditorView_emptyColor, Color.DKGRAY);
        defaultTextSize = a.getDimension(R.styleable.GiantSquareEditorView_defaultTextSize, defaultTextSize);
        int backgroungId = a.getResourceId(R.styleable.GiantSquareView_selectCheckedDrawable, -1);
        if (backgroungId != -1) {
            emptryBackgroung = BitmapFactory.decodeResource(context.getResources(), backgroungId);
        }
        emptyText = a.getString(R.styleable.GiantSquareEditorView_emptyText);
    }

    public Bitmap getBitamp() {
        return photoEditorImage.getBitmap();
    }

    /**
     *
     */
    public void rotateImage(float angle) {
        Bitmap photoEditorImageBitmap = photoEditorImage.getBitmap();
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            Bitmap bitmap = Bitmap.createBitmap(photoEditorImageBitmap, 0, 0, photoEditorImageBitmap.getWidth(), photoEditorImageBitmap.getHeight(), matrix, true);
            if (bitmap != null) {
                setImageBitmap(bitmap);
            }
        } catch (OutOfMemoryError error) {
            Log.e(TAG, "Too low mamory for rotate image", error);
            //Toast.makeText(getContext(), R.string.error_low_memory, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     */
    public void horizontalFlip() {
        Bitmap photoEditorImageBitmap = photoEditorImage.getBitmap();
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        Bitmap bitmap = Bitmap.createBitmap(photoEditorImageBitmap, 0, 0, photoEditorImageBitmap.getWidth(), photoEditorImageBitmap.getHeight(), matrix, false);
        if (bitmap != null) {
            bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            setImageBitmap(bitmap);
        }
    }

    /**
     *
     */
    public void verticalFlip() {
        Bitmap photoEditorImageBitmap = photoEditorImage.getBitmap();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap bitmap = Bitmap.createBitmap(photoEditorImageBitmap, 0, 0, photoEditorImageBitmap.getWidth(), photoEditorImageBitmap.getHeight(), matrix, false);
        if (bitmap != null) {
            bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            setImageBitmap(bitmap);
        }
    }

    /**
     *
     */
    public void doBrightness(int value) {
        int width = getBitamp().getWidth();
        int height = getBitamp().getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, getBitamp().getConfig());
        if (bitmap != null) {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    // get pixel color
                    int pixel = getBitamp().getPixel(x, y);
                    int A = Color.alpha(pixel);
                    int R = Color.red(pixel);
                    int G = Color.green(pixel);
                    int B = Color.blue(pixel);

                    // increase/decrease each channel
                    R += value;
                    if (R > 255) {
                        R = 255;
                    } else if (R < 0) {
                        R = 0;
                    }

                    G += value;
                    if (G > 255) {
                        G = 255;
                    } else if (G < 0) {
                        G = 0;
                    }

                    B += value;
                    if (B > 255) {
                        B = 255;
                    } else if (B < 0) {
                        B = 0;
                    }

                    // apply new pixel color to output bitmap
                    bitmap.setPixel(x, y, Color.argb(A, R, G, B));
                }
            }

            setImageBitmap(bitmap);
        }
    }

    private void drawStickers(Canvas canvas, Paint paint) {
        if (isSaveInProccess) {
            for (PhotoEditorText text : textsList) {
                paint.setTypeface(text.getTypeface());
                float pixelDensity = scalingForSave;
                paint.setTextSize(text.getSize() * pixelDensity);
                paint.setColor(text.getColor());
                canvas.drawText(text.getText(), text.getX() * pixelDensity, text.getY() * pixelDensity + paint.getTextSize(), paint);
            }
        } else {
            for (int i = 0; i < textsList.size(); i++) {
                PhotoEditorText text = textsList.get(i);
                text.setPaintParams(paint);
                canvas.drawText(text.getText(), text.getX(), text.getY() + text.getSize(), paint);
                if (drawTextBorder) {
                    if (checkedTextId == -1 || (checkedTextId != -1 && checkedTextId == i)) {
                        paint.setColor(PhotoEditorText.TEXT_BACKGROUND_COLOR);
                        canvas.drawRect(text.getTextArea(), paint);
                    }
                }
            }
        }
    }

    private void drawTexts(Canvas canvas, Paint paint) {
        if (isSaveInProccess) {
            for (PhotoEditorText text : textsList) {
                paint.setTypeface(text.getTypeface());
                float pixelDensity = scalingForSave;
                paint.setTextSize(text.getSize() * pixelDensity);
                paint.setColor(text.getColor());
                canvas.drawText(text.getText(), text.getX() * pixelDensity, text.getY() * pixelDensity + paint.getTextSize(), paint);
            }
        } else {
            for (int i = 0; i < textsList.size(); i++) {
                PhotoEditorText text = textsList.get(i);
                text.setPaintParams(paint);
                canvas.drawText(text.getText(), text.getX(), text.getY() + text.getSize(), paint);
                if (drawTextBorder) {
                    if (checkedTextId == -1 || (checkedTextId != -1 && checkedTextId == i)) {
                        paint.setColor(PhotoEditorText.TEXT_BACKGROUND_COLOR);
                        canvas.drawRect(text.getTextArea(), paint);
                    }
                }
            }
        }
    }

    public boolean isFreeTransform() {
        return freeTransform;
    }

    public void setFreeTransform(boolean freeTransform) {
        this.freeTransform = freeTransform;
    }

    public int getImagePaddingColor() {
        return imagePaddingColor;
    }

    public void setImagePaddingColor(int imagePaddingColor) {
        this.imagePaddingColor = imagePaddingColor;
        invalidate();
    }

    public int getImagePadding() {
        if (isSaveInProccess) {
            float pixelDensity = (float) (imagePart.width()) / (imageSquare.width());
            return (int) (imagePadding * pixelDensity);
        } else {
            return imagePadding;
        }
    }

    public void setImagePadding(int padding) {
        if (imageCentering == true) {
            setFirstImageState();
            imageCentering = false;
        } else {
            this.imagePadding = padding;
            calculatePadding = true;
        }
        invalidate();
        requestLayout();
    }

    public void addText(PhotoEditorText text) {
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
    }

    public void setMaxScaleSize(float scale) {
        photoEditorImage.setMaxScaleSize(scale);
    }

    public Typeface getTextTypeface() {
        return textTypeface;
    }

    public void setTextTypeface(Typeface textTypeface) {
        this.textTypeface = textTypeface;
    }

    public int getDefaultTextColor() {
        return defaultTextColor;
    }

    public void setDefaultTextColor(int defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
    }

    public float getDefaultTextSize() {
        return defaultTextSize;
    }

    public void setDefaultTextSize(float defaultTextSize) {
        this.defaultTextSize = defaultTextSize;
    }

    public int getEmptyColor() {
        return emptyColor;
    }

    public void setEmptyColor(int emptyColor) {
        this.emptyColor = emptyColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        if (isEmpty()) {
            if (emptryBackgroung == null) {
                paint.setColor(emptyColor);
                canvas.drawRect(allSquare, paint);
            } else {
                canvas.drawBitmap(emptryBackgroung, null, allSquare, paint);
            }
            if (!TextUtils.isEmpty(emptyText)) {
                Paint paint1 = new Paint();
                paint1.setTypeface(textTypeface);
                paint1.setColor(Color.argb(128, 0, 0, 0));
                float textSize = 10f;
                paint1.setAntiAlias(true);
                paint1.setTextSize(textSize);
                Rect bounds = new Rect();
                paint1.getTextBounds(emptyText, 0, emptyText.indexOf("\n"), bounds);
                while ((float) bounds.width() / allSquare.width() < 0.7) {
                    paint1.setTextSize(++textSize);
                    paint1.getTextBounds(emptyText, 0, emptyText.indexOf("\n"), bounds);
                }
                int y = allSquare.centerY() + bounds.height() * 2;
                for (String line : emptyText.split("\n")) {
                    paint1.getTextBounds(line, 0, line.length(), bounds);
                    canvas.drawText(line, allSquare.centerX() - bounds.width() / 2, y, paint1);
                    y += bounds.height() + bounds.height() / 2;
                }
                //  canvas.drawText(emptyText, allSquare.centerX() - bounds.width() / 2, allSquare.centerY() + bounds.height() * 2, paint1);
                paint1.setTextSize(textSize + 60);
                paint1.getTextBounds("+", 0, 1, bounds);
                canvas.drawText("+", allSquare.centerX() - bounds.width() / 2, allSquare.centerY(), paint1);
            }
        } else {
            canvas.drawColor(imagePaddingColor);
            drawImage(canvas, paint);
            drawTexts(canvas, new Paint());
        }
    }

    private void drawImage(Canvas canvas, Paint paint) {
        calculateImagePart();
        Matrix matrix = new Matrix();
        float scale = photoEditorImage.getScale() / getImageScaleForUnionArea(imageSquare, photoEditorImage);
        if (isSaveInProccess) {
            matrix.setScale(scale * scalingForSave, scale * scalingForSave);
        } else {
            matrix.setScale(scale, scale);
        }
        matrix.postRotate(-photoEditorImage.getRoationDegrees());
        float ab = (float) (mRotateCenterDistance * Math.cos(Math.toRadians(mRotateCenterAngle)));
        float aB = (float) (mRotateCenterDistance * Math.cos(Math.toRadians(mRotateCenterAngle - mIndependentAngle))) * mIndependentScale;
        float ac = (float) (mRotateCenterDistance * Math.sin(Math.toRadians(mRotateCenterAngle)));
        float aC = (float) (mRotateCenterDistance * Math.sin(Math.toRadians(mRotateCenterAngle - mIndependentAngle))) * mIndependentScale;

        if (isSaveInProccess) {
            matrix.postTranslate(scalingForSave * (ab - aB - photoEditorImage.getLeft()), scalingForSave * (ac - aC - photoEditorImage.getTop()));
        } else {
            matrix.postTranslate((ab - aB) - photoEditorImage.getLeft(), (ac - aC) - photoEditorImage.getTop());
        }
        canvas.drawBitmap(photoEditorImage.getBitmap(), matrix, paint);
    }


    private void calculateImagePart() {
        if (!isEmpty()) {

            PhotoEditorImage image = photoEditorImage;
            Rect unionImageArea = imageSquare;
            float imageScaleForWrapArea = getImageScaleForUnionArea(unionImageArea, image);
            image.setMinLeft(-Integer.MAX_VALUE);
            image.setMinTop(-Integer.MAX_VALUE);
            image.setMaxTop(Integer.MAX_VALUE);
            image.setMaxLeft(Integer.MAX_VALUE);

            Rect sqRect = imageSquare;
            int left = Math.round((sqRect.left - unionImageArea.left) * imageScaleForWrapArea / image.getScale() + image.getLeft());
            int top = Math.round((sqRect.top - unionImageArea.top) * imageScaleForWrapArea / image.getScale() + image.getTop());
            int right = Math.round((sqRect.right - unionImageArea.left) * imageScaleForWrapArea / image.getScale() + image.getLeft());
            int bottom = Math.round((sqRect.bottom - unionImageArea.top) * imageScaleForWrapArea / image.getScale() + image.getTop());
            imagePart = new Rect(left, top, right, bottom);

        }

    }

    private float getImageScaleForUnionArea(Rect unionImageArea, PhotoEditorImage image) {
        int biggerImageAreaSideSize = unionImageArea.width() < unionImageArea.height() ? unionImageArea.height() : unionImageArea.width();
        float imageScaleForWrapArea;
        if (image.getHeight() > image.getWidth()) {
            imageScaleForWrapArea = (float) image.getHeight() / biggerImageAreaSideSize;
        } else {
            imageScaleForWrapArea = (float) image.getWidth() / biggerImageAreaSideSize;
        }
        return imageScaleForWrapArea;
    }

    public boolean isEmpty() {
        return photoEditorImage == null || photoEditorImage.getBitmap() == null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        calculateActiveSquare(parentWidth, parentHeight);
        setMeasuredDimension(imageSquare.width() + 2 * (getImagePadding()), imageSquare.height() + 2 * (getImagePadding()));
    }

    public void calculateActiveSquare(int width, int height) {

        Rect rect;
        if (width <= height) {
            rect = new Rect(0, 0, width, width);
        } else {
            int addPadding = (width - height) / 2;
            rect = new Rect(addPadding, 0, height + addPadding, height);
        }
        allSquare = rect;
        if (imageSquare != null && photoEditorImage != null && calculatePadding) {
            if (imageSquare.left != getImagePadding()) {
                float scale2 = getImageScaleForUnionArea(imageSquare, photoEditorImage);
                int translateForCenteringImageLeft = (int) ((imageSquare.width() - photoEditorImage.getWidth() / scale2) / 2);
                int translateFroCenteringImageTop = (int) ((imageSquare.height() - photoEditorImage.getHeight() / scale2) / 2);
                photoEditorImage.setLeft(photoEditorImage.getLeft() + (translateForCenteringImageLeft + imageSquare.left));
                photoEditorImage.setTop(photoEditorImage.getTop() + (translateFroCenteringImageTop + imageSquare.top));
            }
            imageSquare = new Rect(rect.left + getImagePadding(), rect.top + getImagePadding(), rect.right - getImagePadding(), rect.bottom - getImagePadding());
            float scale3 = getImageScaleForUnionArea(imageSquare, photoEditorImage);
            int translateFroCenteringImageLeft = (int) ((imageSquare.width() - photoEditorImage.getWidth() / scale3) / 2);
            int translateFroCenteringImageTop = (int) ((imageSquare.height() - photoEditorImage.getHeight() / scale3) / 2);
            photoEditorImage.setLeft(photoEditorImage.getLeft() - (translateFroCenteringImageLeft + imageSquare.left));
            photoEditorImage.setTop(photoEditorImage.getTop() - (translateFroCenteringImageTop + imageSquare.top));
            calculatePadding = false;
        } else {
            imageSquare = new Rect(rect.left + getImagePadding(), rect.top + getImagePadding(), rect.right - getImagePadding(), rect.bottom - getImagePadding());
        }

    }

    public void setFirstImageState() {
        if (imageSquare != null && photoEditorImage != null) {
            photoEditorImage.setLeft(0);
            photoEditorImage.setTop(0);
            photoEditorImage.setRoationDegrees(0);
            photoEditorImage.setScale(1);
            mIndependentScale = 1;
            mIndependentAngle = 0;
            float scale3 = getImageScaleForUnionArea(imageSquare, photoEditorImage);
            int translateFroCenteringImageLeft = (int) ((imageSquare.width() - photoEditorImage.getWidth() / scale3) / 2);
            int translateFroCenteringImageTop = (int) ((imageSquare.height() - photoEditorImage.getHeight() / scale3) / 2);
            photoEditorImage.setLeft(photoEditorImage.getLeft() - (translateFroCenteringImageLeft + imageSquare.left));
            photoEditorImage.setTop(photoEditorImage.getTop() - (translateFroCenteringImageTop + imageSquare.top));
        }
        invalidate();
        requestLayout();
    }


    private Bitmap createBitmap(int width, int height, int padding) throws OutOfMemoryError {
        int newWidth = width + padding * 2;
        int newHeight = height + padding * 2;
        return createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
    }

    public void setImageBitmap(Bitmap bitmap) {
        imageList.add(bitmap);
        photoEditorImage = new PhotoEditorImage();
        photoEditorImage.setBitmap(bitmap);
        calculatePadding = true;
        this.initialize();
        invalidate();
        requestLayout();
    }

    public Bitmap createBitmap(int newWidth, int newHeight, Bitmap.Config config) throws OutOfMemoryError {
        Bitmap bmp = null;
        try {
            bmp = Bitmap.createBitmap(newWidth, newHeight, config);
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "out memroy", e);
            if (newWidth > 3 && newHeight > 3) {
                bmp = createBitmap((int) (newWidth * 0.75), (int) (newHeight * 0.75), config);
            } else {
                throw new OutOfMemoryError("Cann't create image for save");
            }
        }
        return bmp;
    }

    public boolean isChangeImage() {
        return changeImage;
    }

    private void initialize() {

        setOnTouchListener(this);
    }

    private float distance(float x0, float x1, float y0, float y1) {

        float x = x0 - x1;
        float y = y0 - y1;
        return (float) Math.sqrt(x * x + y * y);
    }

    private float dispDistance() {

        return (float) Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
    }

    private void findCheckedText(int x, int y) {
        for (int i = textsList.size() - 1; i >= 0; i--) {
            if (textsList.get(i).getTextArea().contains(x, y)) {
                checkedTextId = i;
                return;
            }
        }
        checkedTextId = -1;
    }

    private LongClickAsyncTask longClickAsyncTask;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (!isEmpty()) {
            int touchCount = event.getPointerCount();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_1_DOWN:
                case MotionEvent.ACTION_POINTER_2_DOWN:
                    if (touchCount >= 2) {
                        float distance = distance(event.getX(0), event.getX(1), event.getY(0), event.getY(1));
                        if (isFreeTransform()) {
                            if (checkedTextId == -1) {
                                float ab = (float) (mRotateCenterDistance * Math.cos(Math.toRadians(mRotateCenterAngle)));
                                float aB = (float) (mRotateCenterDistance * Math.cos(Math.toRadians(mRotateCenterAngle - mIndependentAngle))) * mIndependentScale;
                                float ac = (float) (mRotateCenterDistance * Math.sin(Math.toRadians(mRotateCenterAngle)));
                                float aC = (float) (mRotateCenterDistance * Math.sin(Math.toRadians(mRotateCenterAngle - mIndependentAngle))) * mIndependentScale;
                                photoEditorImage.setLeft((int) (photoEditorImage.getLeft() + (aB - ab)));
                                photoEditorImage.setTop((int) (photoEditorImage.getTop() + (aC - ac)));
                                mIndependentAngle = 0;
                                mIndependentScale = 1f;
                                fingersAngle = rotation(event);
                                float centerX = (event.getX(0) + event.getX(1)) / 2 + photoEditorImage.getLeft();
                                float centerY = (event.getY(0) + event.getY(1)) / 2 + photoEditorImage.getTop();
                                mRotateCenterAngle = Math.toDegrees(Math.atan(centerY / centerX));
                                mRotateCenterDistance = centerX / Math.cos(Math.toRadians(mRotateCenterAngle));
                            }
                        }
                        mPrevDistance = distance;
                        isScaling = true;
                        longClick = false;
                    } else {
                        drawTextBorder = true;
                        int DOUBLE_TAP_SECOND = 400;
                        if (System.currentTimeMillis() <= mLastTime + DOUBLE_TAP_SECOND) {
                            if (30 > Math.abs(mPrevMoveX - event.getX()) + Math.abs(mPrevMoveY - event.getY())) {
                                isDoubleTap = true;
                                mDoubleTapX = (int) event.getX();
                                mDoubleTapY = (int) event.getY();
                            }
                        }
                        mLastTime = System.currentTimeMillis();
                        mPrevMoveX = (int) event.getX();
                        mPrevMoveY = (int) event.getY();
                        findCheckedText(mPrevMoveX, mPrevMoveY);
                        longClick = true;
                        longClickAsyncTask = new LongClickAsyncTask(this);
                        longClickAsyncTask.execute(checkedTextId);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    imageCentering = true;
                    if (!isEmpty()) {
                        if (touchCount >= 2 && isScaling) {
                            float dist = distance(event.getX(0), event.getX(1), event.getY(0), event.getY(1));
                            float scale = (dist - mPrevDistance) / dispDistance();
                            mPrevDistance = dist;
                            scale += 1;
                            scale = scale * scale;
                            if (checkedTextId == -1) {
                                if (isFreeTransform()) {
                                    photoEditorImage.setRoationDegrees(photoEditorImage.getRoationDegrees() + fingersAngle - rotation(event));
                                    mIndependentAngle = mIndependentAngle + fingersAngle - rotation(event);
                                    fingersAngle = rotation(event);
                                    if (photoEditorImage.getScale() * scale > 0 && photoEditorImage.getScale() * scale < photoEditorImage.getMaxScale()) {
                                        mIndependentScale = mIndependentScale * scale;
                                    }
                                    photoEditorImage.setFreeScale(photoEditorImage.getScale() * scale);
                                }
                            } else {
                                textsList.get(checkedTextId).setSize(textsList.get(checkedTextId).getSize() * scale);
                            }
                            longClick = false;
                        } else if (!isScaling) {
                            int distanceX = mPrevMoveX - (int) event.getX();
                            int distanceY = mPrevMoveY - (int) event.getY();
                            if (8 < Math.abs(distanceX) + Math.abs(distanceY)) {
                                longClick = false;
                            }
                            mPrevMoveX = (int) event.getX();
                            mPrevMoveY = (int) event.getY();
                            if (checkedTextId == -1) {
                                if (isFreeTransform()) {
                                    photoEditorImage.setLeft(photoEditorImage.getLeft() + Math.round(distanceX));
                                    photoEditorImage.setTop(photoEditorImage.getTop() + Math.round(distanceY));
                                    if (longClick && System.currentTimeMillis() > mLastTime + LONG_PRESS_MILLISECOND + 100) {
                                        if (onSquareEditorPictureClickListener != null) {
                                            longClick = false;
                                        }
                                    }
                                }
                            } else {
                                PhotoEditorText text = textsList.get(checkedTextId);
                                text.setX(text.getX() - Math.round(distanceX));
                                text.setY(text.getY() - Math.round(distanceY));
                                if (longClick && System.currentTimeMillis() > mLastTime + LONG_PRESS_TEXT_MILLISECOND) {
                                    if (onSquareEditorPictureClickListener != null) {
                                        longClick = false;
                                    }
                                }
                            }
                        }
                        calculateImagePart();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_POINTER_2_UP:
                    if (longClickAsyncTask != null) {
                        longClickAsyncTask.cancel(true);
                    }
                    if (event.getPointerCount() <= 1) {
                        drawTextBorder = false;
                        longClick = false;
                        isScaling = false;
                        if (isDoubleTap) {
                            if (30 > Math.abs(mDoubleTapX - event.getX()) + Math.abs(mDoubleTapY - event.getY())) {
                                // angle = 90;
                                if (checkedTextId == -1) {
                                    //  photoEditorImage.setBitmap(rotateImage(photoEditorImage.getBitmap(), angle));
                                } else {
                                    if (squareEditorListener != null) {
                                        squareEditorListener.editText(textsList.get(checkedTextId));
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
            invalidate();
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param path - directory path to saving image with padding and with text/
     * @return path to saved image
     */
    public String[] saveImages(String path) throws IOException, OutOfMemoryError {
        if (!isEmpty()) {
            List<String> files = new LinkedList<String>();
            PhotoEditorImage image = photoEditorImage;
            long saveImagesTime = System.currentTimeMillis();
            String fileName = String.format("%d_editor.jpg", saveImagesTime);
            File file = new File(path, fileName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file, false);
                float pixelDensity = (float) (imagePart.width()) / (imageSquare.width());
                Bitmap processedBitmap = createBitmap(imagePart.width(), imagePart.height(), (int) ((getImagePadding() * pixelDensity)));
                scalingForSave = (float) processedBitmap.getWidth() / allSquare.width();
                if (processedBitmap != null && processedBitmap.getWidth() != 0) {
                    isSaveInProccess = true;
                    Canvas canvas = new Canvas(processedBitmap);
                    draw(canvas);
                    if (processedBitmap.compress(
                            Bitmap.CompressFormat.JPEG, 100, fos)) {
                        files.add(file.getAbsolutePath());
                    }
                    isSaveInProccess = false;
                    // FileUtil.recycle(processedBitmap);
                }
            } catch (IOException e) {
                throw new IOException("Error to save file! File = " + file);
            } catch (OutOfMemoryError e) {
                Toast.makeText(getContext(), "Out of memory!", Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error to close FOS!", e);
                }
            }
            return files.toArray(new String[0]);
        }

        return null;
    }

    public String[] saveImages(String path, String fileName) throws IOException, OutOfMemoryError {
        if (!isEmpty()) {
            List<String> files = new LinkedList<String>();
            PhotoEditorImage image = photoEditorImage;
            File file = new File(path, fileName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file, false);
                float pixelDensity = (float) (imagePart.width()) / (imageSquare.width());
                Bitmap processedBitmap = createBitmap(imagePart.width(), imagePart.height(), (int) ((getImagePadding() * pixelDensity)));
                scalingForSave = (float) processedBitmap.getWidth() / allSquare.width();
                if (processedBitmap != null && processedBitmap.getWidth() != 0) {
                    isSaveInProccess = true;
                    Canvas canvas = new Canvas(processedBitmap);
                    draw(canvas);
                    if (processedBitmap.compress(
                            Bitmap.CompressFormat.JPEG, 100, fos)) {
                        files.add(file.getAbsolutePath());
                    }
                    isSaveInProccess = false;
                    //FileUtil.recycle(processedBitmap);
                }
            } catch (IOException e) {
                throw new IOException("Error to save file! File = " + file);
            } catch (OutOfMemoryError e) {
                Toast.makeText(getContext(), "Out of memory!", Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error to close FOS!", e);
                }
            }
            return files.toArray(new String[0]);
        }

        return null;
    }

    /**
     * Determine the degree between the first two fingers
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    public OnSquareEditorListener getSquareEditorListener() {
        return squareEditorListener;
    }

    public void setSquareEditorListener(OnSquareEditorListener squareEditorListener) {
        this.squareEditorListener = squareEditorListener;
    }

    public void setChangeImage(boolean changeImage) {
        this.changeImage = changeImage;
    }

    public void setOnSquareEditorPictureClickListener(OnSquareEditorPictureClickListener onSquareEditorPictureClickListener) {
        this.onSquareEditorPictureClickListener = onSquareEditorPictureClickListener;
    }

    public void deleteText(PhotoEditorText text) {
        if (textsList != null && textsList.contains(text)) {
            textsList.remove(text);
            invalidate();
            requestLayout();
        }
    }

    public void clearAll() {
        if (photoEditorImage != null && photoEditorImage.getBitmap() != null) {
            //FileUtil.recycle(photoEditorImage.getBitmap());
            photoEditorImage.setBitmap(null);
        }
        photoEditorImage = null;
    }

    public void duplicateText(int id) {
        if (textsList != null && id >= 0 && id < textsList.size()) {
            PhotoEditorText initText = textsList.get(id);
            PhotoEditorText newText = new PhotoEditorText();
            newText.setColor(initText.getColor());
            newText.setSize(initText.getSize());
            newText.setText(initText.getText());
            newText.setTypeface(initText.getTypeface());
            newText.setTypefacePath(initText.getTypefacePath());
            textsList.add(newText);
        }
    }


    public interface OnSquareEditorListener {
        public void editText(PhotoEditorText giantSquareText);
    }

    public interface OnSquareEditorPictureClickListener {
        void onPictureLongClick();

        void onTextLongClick(int id);
    }

    private static class LongClickAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private final PhotoEditorView engine;

        private LongClickAsyncTask(PhotoEditorView engine) {
            this.engine = engine;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                if (params[0] == -1) {
                    Thread.sleep(LONG_PRESS_MILLISECOND);
                } else {
                    Thread.sleep(LONG_PRESS_TEXT_MILLISECOND);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            super.onPostExecute(aVoid);
            if (engine.longClick && !engine.isEmpty() && engine.onSquareEditorPictureClickListener != null) {
                if (aVoid == -1) {
                    engine.onSquareEditorPictureClickListener.onPictureLongClick();
                } else {
                    engine.onSquareEditorPictureClickListener.onTextLongClick(aVoid);
                }
            }
        }
    }
}

