package net.iquesoft.iquephoto.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Drawing;
import net.iquesoft.iquephoto.model.EditorImage;
import net.iquesoft.iquephoto.model.Text;
import net.iquesoft.iquephoto.model.Sticker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EditorView extends View implements View.OnTouchListener {

    private final String TAG = EditorView.class.getSimpleName();

    private Context context;

    private static final int LONG_PRESS_MILLISECOND = 1000;
    private static final long LONG_PRESS_TEXT_MILLISECOND = 2000;

    private List<Bitmap> imageList = new ArrayList<>();

    private EditorImage editorImage;

    private float brightnessValue = 0;

    private boolean cropActivated = false;

    // For drawing
    private boolean drawingActivated = false;
    private float drawingX, drawingY;
    private Paint drawingPaint;
    private Path drawingPath;
    private Paint drawingCirclePaint = new Paint();
    private Path drawingCirclePath;
    //private Canvas drawingCanvas;
    private static final float TOUCH_TOLERANCE = 4;
    private ArrayList<Drawing> drawings = new ArrayList<>();

    // For meme
    private String topMemeText;
    private String bottomMemeText;

    // For text
    private boolean textActivated = false;
    private int checkedTextId = -1;
    private boolean deleteTextActivated = false;
    private float defaultTextSize = 22f;
    private int defaultTextColor = 0xFF000000;
    private Typeface textTypeface;
    private List<Text> textsList = new LinkedList<Text>();

    // For stickers
    private int checkedStickerId = -1;
    private List<Sticker> stickersList = new LinkedList<Sticker>();

    private boolean hasFilter = false;

    private ColorMatrix colorMatrix;

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
    private int emptyColor;

    private Rect imageRect;
    private Rect allSquare;

    private int imagePadding;
    private Rect imagePart;
    private int imagePaddingColor = Color.BLACK;
    private boolean freeTransform;

    private Bitmap emptryBackgroung = null;
    private OnSquareEditorListener squareEditorListener;
    private String emptyText;
    private boolean isSaveInProccess;
    private float scalingForSave = 1;
    private boolean calculatePadding = true;
    private boolean longClick;
    private boolean changeImage;

    private OnSquareEditorPictureClickListener onSquareEditorPictureClickListener;

    private float cropLineWidth = 10f;
    private float cropCornerWidth = 10f;
    private float cropCornerLength = 50f;
    private float cropOffset = cropLineWidth / 4;
    private float cropOffset2 = cropLineWidth;

    private int cropLineColor = getContext().getResources().getColor(R.color.white);
    private int cropCornerColor = getContext().getResources().getColor(R.color.white);
    private int cropShadowColor = getContext().getResources().getColor(R.color.colorBackground);

    private boolean imageCentering;

    private boolean drawTextBorder;
    private boolean drawStickerBorder;

    public EditorView(Context context) {
        super(context);
        this.context = context;

    }

    public EditorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        drawingPaint = new Paint();
        drawingPath = new Path();
        drawingCirclePath = new Path();
        drawingCirclePaint = new Paint();

        this.context = context;
    }

    public void setDrawingActivated(boolean drawingActivated) {
        this.drawingActivated = drawingActivated;
        Log.i("Drawing", String.valueOf(drawingActivated));
        drawingCirclePaint.setAntiAlias(true);
        drawingCirclePaint.setColor(Color.RED);
        drawingCirclePaint.setStyle(Paint.Style.STROKE);
        drawingCirclePaint.setStrokeJoin(Paint.Join.MITER);
        drawingCirclePaint.setStrokeWidth(4f);

        drawingPaint.setAntiAlias(true);
        drawingPaint.setDither(true);
        drawingPaint.setColor(Color.GREEN);
        drawingPaint.setStyle(Paint.Style.STROKE);
        drawingPaint.setStrokeJoin(Paint.Join.ROUND);
        drawingPaint.setStrokeCap(Paint.Cap.ROUND);
        drawingPaint.setStrokeWidth(12);
    }

    public void setDrawingColor(int color) {
        Log.i("Drawing", "Color: " + color);
        drawingPaint.setColor(context.getResources().getColor(color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();

        canvas.drawColor(imagePaddingColor);
        drawImage(canvas, paint);
        if (hasFilter) {
            Paint filterPaint = new Paint();
            filterPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            drawImage(canvas, filterPaint);
        }
        drawTexts(canvas, new Paint());
        if (stickersList.size() > 0) {
            drawSticker(canvas, new Paint());
        }
        if (brightnessValue != 0) {
            drawImage(canvas, getBrightnessMatrix(brightnessValue));
        }
        if (topMemeText != null) {
            drawTopMemeText(canvas);
        }
        if (bottomMemeText != null) {
            drawBottomMemeText(canvas);
        }
        if (drawings.size() > 0) {
            for (Drawing drawing : drawings) {
                canvas.drawPath(drawing.getPath(), drawing.getPaint());

            }
           /* try {
                canvas.drawPath(drawingPath, drawingPaint);
                canvas.drawPath(drawingCirclePath, drawingCirclePaint);
            } catch (NullPointerException e) {
                Log.i("Drawing", "is null!");
            }*/
        }

    }

    private void drawingStart(float x, float y) {
        drawingPath.reset();
        Log.i("Drawing", "Start");
        drawingPath.moveTo(x, y);
        drawingX = x;
        drawingY = y;
    }

    private void drawingMove(float x, float y) {
        float dX = Math.abs(x - drawingX);
        float dY = Math.abs(y - drawingY);
        Log.i("Drawing", "Move");
        if (dX >= TOUCH_TOLERANCE || dY >= TOUCH_TOLERANCE) {
            drawingPath.quadTo(drawingX, drawingY, (x + drawingX) / 2, (y + drawingY) / 2);
            drawingX = x;
            drawingY = y;

            drawingCirclePath.reset();
            drawingCirclePath.addCircle(drawingX, drawingY, 30, Path.Direction.CW);
        }
    }

    // FIXME: Problem with drawing from List of Drawings.
    private void drawingStop() {
        drawingPath.lineTo(drawingX, drawingY);
        drawingCirclePath.reset();
        Paint paint = drawingPaint;
        Path path = drawingPath;
        drawings.add(new Drawing(paint, path));
        //drawingPath.reset();
        Log.i("Drawing", "Stop");
        Log.i("Drawing", String.valueOf(drawings.size()) + " time(-s).");
        //drawingCanvas.drawPath(drawingPath, drawingPaint);
        //drawingPath.reset();
    }

    public Bitmap getBitamp() {
        return editorImage.getBitmap();
    }

    public List<Text> getTextsList() {
        return textsList;
    }

    public int getCheckedTextId() {
        return checkedTextId;
    }

    public void setImageBitmap(Bitmap bitmap) {
        //imageList.add(bitmap);
        editorImage = new EditorImage();
        editorImage.setBitmap(bitmap);
        calculatePadding = true;
        this.initialize();
        invalidate();
        requestLayout();
    }

    public void setHasNotFiler() {
        this.hasFilter = false;
        this.invalidate();
    }

    public void drawTopMemeText(Canvas canvas) {
        final float x = (getBitamp().getWidth() / 2) - (getMemePaint().measureText(topMemeText) / 2);
        final float y = 85;

        canvas.drawText(topMemeText, x, y, getMemePaint());
        canvas.drawText(topMemeText, x, y, getMemeStrokePaint());
    }

    public void drawBottomMemeText(Canvas canvas) {
        final float x = (getBitamp().getWidth() / 2) - (getMemePaint().measureText(topMemeText) / 2);
        final float y = (getBitamp().getHeight() - 42.5f);

        canvas.drawText(bottomMemeText, x, y, getMemePaint());
        canvas.drawText(bottomMemeText, x, y, getMemeStrokePaint());
    }

    private Paint getMemeStrokePaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(75);
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Impact.ttf"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        return paint;
    }

    private Paint getMemePaint() {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(75);
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Impact.ttf"));
        paint.setStrokeWidth(2);

        return paint;
    }

    /**
     * Rotate editorImage.
     */
    // Todo: Make it in new thread.
    public void rotateImage(float angle) {
        Bitmap photoEditorImageBitmap = editorImage.getBitmap();
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            Bitmap bitmap = Bitmap.createBitmap(photoEditorImageBitmap, 0, 0, photoEditorImageBitmap.getWidth(), photoEditorImageBitmap.getHeight(), matrix, true);
            if (bitmap != null) {
                setImageBitmap(bitmap);
            }
        } catch (OutOfMemoryError error) {
            Log.e(TAG, "Too low mamory for rotate editorImage", error);
            //Toast.makeText(getContext(), R.string.error_low_memory, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     */
    // Todo: Make it in new thread.
    public void horizontalFlip() {
        Bitmap photoEditorImageBitmap = editorImage.getBitmap();
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
    // Todo: Make it in new thread.
    public void verticalFlip() {
        Bitmap photoEditorImageBitmap = editorImage.getBitmap();
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
    private Paint getBrightnessMatrix(float value) {
        Log.i("Brightness", "Value: " + String.valueOf(value));
        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.set(new float[]{1, 0, 0, 0, value,
                0, 1, 0, 0, value,
                0, 0, 1, 0, value,
                0, 0, 0, 1, 0});
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (hasFilter) {
            ColorMatrix brightnessMatrixWithFilter = new ColorMatrix();
            brightnessMatrixWithFilter.setConcat(brightnessMatrix, colorMatrix);
            paint.setColorFilter(new ColorMatrixColorFilter(brightnessMatrixWithFilter));
        } else {
            paint.setColorFilter(new ColorMatrixColorFilter(brightnessMatrix));
        }

        return paint;
    }

    private Paint getPaintForContrast(float value) {
        float scale = value + 1.f;
        float translate = (-.5f * scale + .5f) * 255.f;
        float[] array = new float[]{
                scale, 0, 0, 0, 0,
                0, scale, 0, 0, 0,
                0, 0, scale, 0, 0,
                0, 0, 0, 1, 0};
        ColorMatrix matrix = new ColorMatrix(array);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        return paint;
    }

    private Paint getPaintForSaturation(float value) {
        // Todo: Almost done Saturation.
        float sat = 0.5f;
        if (value > 0) {
            sat = 0.5f + ((value / 100) / 2);
        } else if (value < 0) {
            sat = 0.5f - ((value / -100) / 2);
        }
        Log.i("Saturation", String.valueOf(sat));
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(sat);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        return paint;
    }

    public float getBrightnessValue() {
        return brightnessValue;
    }

    public void setBrightnessValue(float brightnessValue) {
        this.brightnessValue = brightnessValue;
        invalidate();
    }

    public void setFilter(ColorMatrix colorMatrix) {
        if (colorMatrix != null) {
            hasFilter = true;
            this.colorMatrix = colorMatrix;
            invalidate();
        }
    }

    private void drawStickers(Canvas canvas, Paint paint) {
        if (isSaveInProccess) {
            for (Sticker sticker : stickersList) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), sticker.getImage());
                canvas.drawBitmap(bitmap, 0, 0, paint);
            }
        } else {
            for (int i = 0; i < stickersList.size(); i++) {
                Sticker sticker = stickersList.get(i);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), sticker.getImage());
                canvas.drawBitmap(bitmap, 0, 0, paint);
                //canvas.drawText(text.getText(), text.getX(), text.getY() + text.getSize(), paint);

                // Todo: Draw sticker border.

                if (drawTextBorder) {
                    if (checkedTextId == -1 || (checkedTextId != -1 && checkedTextId == i)) {
                        paint.setColor(Text.TEXT_BACKGROUND_COLOR);
                        canvas.drawRect(sticker.getStickerArea(), paint);
                    }
                }
            }
        }
    }

    /**
     *
     */
    public void addText(Text text) {
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

    /**
     *
     */
    public void addSticker(Sticker sticker) {
        sticker.setX(100);
        sticker.setY(100);
        Log.i("Sticker", getResources().getString(sticker.getTitle()) + " \nX: "
                + sticker.getX() + " \nY: " + sticker.getY());
        this.stickersList.add(sticker);
        invalidate();
    }

    /**
     *
     */
    // Todo: Make text opacity.
    private void drawTexts(Canvas canvas, Paint paint) {
        if (isSaveInProccess) {
            for (Text text : textsList) {
                paint.setTypeface(text.getTypeface());
                float pixelDensity = scalingForSave;
                paint.setTextSize(text.getSize() * pixelDensity);
                paint.setColor(text.getColor());
                paint.setAlpha(text.getOpacity());
                canvas.drawText(text.getText(), text.getX() * pixelDensity, text.getY() * pixelDensity + paint.getTextSize(), paint);
            }
        } else {
            for (int i = 0; i < textsList.size(); i++) {
                Text text = textsList.get(i);
                text.setPaintParams(paint);
                canvas.drawText(text.getText(), text.getX(), text.getY() + text.getSize(), paint);
                if (drawTextBorder) {
                    if (checkedTextId == -1 || (checkedTextId != -1 && checkedTextId == i)) {
                        paint.setColor(Text.TEXT_BACKGROUND_COLOR);
                        canvas.drawRect(text.getTextArea(), paint);
                    }
                }
            }
        }
    }

    private void findCheckedSticker(int x, int y) {
        for (int i = stickersList.size() - 1; i >= 0; i--) {
            if (stickersList.get(i).getStickerArea().contains(x, y)) {
                checkedStickerId = i;
                Sticker sticker = stickersList.get(i);
                Log.i("Sticker", getResources().getString(sticker.getTitle()) + " \nX: "
                        + sticker.getX() + " \nY: " + sticker.getY());
                return;
            }
        }
        checkedStickerId = -1;
    }

    private void findCheckedText(int x, int y) {
        for (int i = textsList.size() - 1; i >= 0; i--) {
            if (textsList.get(i).getTextArea().contains(x, y)) {
                checkedTextId = i;
                Log.d("Text", textsList.get(i).getText());

                if (deleteTextActivated) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                            /*.setTitle(context.getString(R.string.permission_denied))*/
                            .setMessage(context.getString(R.string.text_delete_alert))
                            .setPositiveButton(context.getString(R.string.yes), (dialogInterface, i2) -> {
                                deleteText(textsList.get(checkedTextId));
                            })
                            .setNegativeButton(context.getString(R.string.no), (dialogInterface, i1) -> {
                                dialogInterface.dismiss();
                            });
                    builder.show();

                } else {
                    return;
                }
            }
        }
        checkedTextId = -1;
    }

    public void deleteText(Text text) {
        if (textsList != null && textsList.contains(text)) {
            textsList.remove(text);
            invalidate();
            requestLayout();
            String string = String.format(getResources().getString(R.string.text_deleted), text.getText());
            Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     */
    private void drawSticker(Canvas canvas, Paint paint) {
        //if (isSaveInProccess) {
        for (Sticker sticker : stickersList) {

            float pixelDensity = scalingForSave;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), sticker.getImage());
            canvas.drawBitmap(bitmap, 100 * pixelDensity, 100 * pixelDensity, paint);
            //if (drawStickerBorder) {
            paint.setColor(Sticker.STICKER_BACKGROUND_COLOR);
            canvas.drawRect(sticker.getStickerArea(), paint);
            //}
        }
        /*} else {
            for (int i = 0; i < textsList.size(); i++) {
                Text text = textsList.get(i);
                text.setPaintParams(paint);
                canvas.drawText(text.getText(), text.getX(), text.getY() + text.getSize(), paint);
                if (drawTextBorder) {
                    if (checkedTextId == -1 || (checkedTextId != -1 && checkedTextId == i)) {
                        paint.setColor(Text.STICKER_BACKGROUND_COLOR);
                        canvas.drawRect(text.getTextArea(), paint);
                    }
                }
            }
        }*/
    }

    private void drawImage(Canvas canvas, Paint paint) {
        calculateImagePart();
        Matrix matrix = new Matrix();
        float scale = editorImage.getScale() / getImageScaleForUnionArea(imageRect, editorImage);
        if (isSaveInProccess) {
            matrix.setScale(scale * scalingForSave, scale * scalingForSave);
        } else {
            matrix.setScale(scale, scale);
        }
        matrix.postRotate(-editorImage.getRoationDegrees());
        float ab = (float) (mRotateCenterDistance * Math.cos(Math.toRadians(mRotateCenterAngle)));
        float aB = (float) (mRotateCenterDistance * Math.cos(Math.toRadians(mRotateCenterAngle - mIndependentAngle))) * mIndependentScale;
        float ac = (float) (mRotateCenterDistance * Math.sin(Math.toRadians(mRotateCenterAngle)));
        float aC = (float) (mRotateCenterDistance * Math.sin(Math.toRadians(mRotateCenterAngle - mIndependentAngle))) * mIndependentScale;

        if (isSaveInProccess) {
            matrix.postTranslate(scalingForSave * (ab - aB - editorImage.getLeft()), scalingForSave * (ac - aC - editorImage.getTop()));
        } else {
            matrix.postTranslate((ab - aB) - editorImage.getLeft(), (ac - aC) - editorImage.getTop());
        }
        canvas.drawBitmap(editorImage.getBitmap(), matrix, paint);
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
            float pixelDensity = (float) (imagePart.width()) / (imageRect.width());
            return (int) (imagePadding * pixelDensity);
        } else {
            return imagePadding;
        }
    }

    public void setImagePadding(int padding) {
        if (imageCentering == true) {
            //setFirstImageState();
            imageCentering = false;
        } else {
            this.imagePadding = padding;
            calculatePadding = true;
        }
        invalidate();
        requestLayout();
    }

    public void setMaxScaleSize(float scale) {
        editorImage.setMaxScaleSize(scale);
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


    private void calculateImagePart() {
        if (!isEmpty()) {

            EditorImage editorImage = this.editorImage;
            Rect unionImageArea = imageRect;
            float imageScaleForWrapArea = getImageScaleForUnionArea(unionImageArea, editorImage);
            editorImage.setMinLeft(-Integer.MAX_VALUE);
            editorImage.setMinTop(-Integer.MAX_VALUE);
            editorImage.setMaxTop(Integer.MAX_VALUE);
            editorImage.setMaxLeft(Integer.MAX_VALUE);

            Rect sqRect = imageRect;
            int left = Math.round((sqRect.left - unionImageArea.left) * imageScaleForWrapArea / editorImage.getScale() + editorImage.getLeft());
            int top = Math.round((sqRect.top - unionImageArea.top) * imageScaleForWrapArea / editorImage.getScale() + editorImage.getTop());
            int right = Math.round((sqRect.right - unionImageArea.left) * imageScaleForWrapArea / editorImage.getScale() + editorImage.getLeft());
            int bottom = Math.round((sqRect.bottom - unionImageArea.top) * imageScaleForWrapArea / editorImage.getScale() + editorImage.getTop());
            imagePart = new Rect(left, top, right, bottom);

        }

    }

    private float getImageScaleForUnionArea(Rect unionImageArea, EditorImage editorImage) {
        int biggerImageAreaSideSize = unionImageArea.width() < unionImageArea.height() ? unionImageArea.height() : unionImageArea.width();
        float imageScaleForWrapArea;
        if (editorImage.getHeight() > editorImage.getWidth()) {
            imageScaleForWrapArea = (float) editorImage.getHeight() / biggerImageAreaSideSize;
        } else {
            imageScaleForWrapArea = (float) editorImage.getWidth() / biggerImageAreaSideSize;
        }
        return imageScaleForWrapArea;
    }

    public boolean isEmpty() {
        return editorImage == null || editorImage.getBitmap() == null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        calculateActiveSquare(parentWidth, parentHeight);
        setMeasuredDimension(imageRect.width() + 2 * (getImagePadding()), imageRect.height() + 2 * (getImagePadding()));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
        if (imageRect != null && editorImage != null && calculatePadding) {
            if (imageRect.left != getImagePadding()) {
                float scale2 = getImageScaleForUnionArea(imageRect, editorImage);
                int translateForCenteringImageLeft = (int) ((imageRect.width() - editorImage.getWidth() / scale2) / 2);
                int translateFroCenteringImageTop = (int) ((imageRect.height() - editorImage.getHeight() / scale2) / 2);
                editorImage.setLeft(editorImage.getLeft() + (translateForCenteringImageLeft + imageRect.left));
                editorImage.setTop(editorImage.getTop() + (translateFroCenteringImageTop + imageRect.top));
            }
            imageRect = new Rect(rect.left + getImagePadding(), rect.top + getImagePadding(), rect.right - getImagePadding(), rect.bottom - getImagePadding());
            float scale3 = getImageScaleForUnionArea(imageRect, editorImage);
            int translateFroCenteringImageLeft = (int) ((imageRect.width() - editorImage.getWidth() / scale3) / 2);
            int translateFroCenteringImageTop = (int) ((imageRect.height() - editorImage.getHeight() / scale3) / 2);
            editorImage.setLeft(editorImage.getLeft() - (translateFroCenteringImageLeft + imageRect.left));
            editorImage.setTop(editorImage.getTop() - (translateFroCenteringImageTop + imageRect.top));
            calculatePadding = false;
        } else {
            imageRect = new Rect(rect.left + getImagePadding(), rect.top + getImagePadding(), rect.right - getImagePadding(), rect.bottom - getImagePadding());
        }

    }

    public void setFirstImageState() {
        if (imageRect != null && editorImage != null) {
            editorImage.setLeft(0);
            editorImage.setTop(0);
            editorImage.setRoationDegrees(0);
            editorImage.setScale(1);
            mIndependentScale = 1;
            mIndependentAngle = 0;
            float scale3 = getImageScaleForUnionArea(imageRect, editorImage);
            int translateFroCenteringImageLeft = (int) ((imageRect.width() - editorImage.getWidth() / scale3) / 2);
            int translateFroCenteringImageTop = (int) ((imageRect.height() - editorImage.getHeight() / scale3) / 2);
            editorImage.setLeft(editorImage.getLeft() - (translateFroCenteringImageLeft + imageRect.left));
            editorImage.setTop(editorImage.getTop() - (translateFroCenteringImageTop + imageRect.top));
        }
        invalidate();
        requestLayout();
    }

    private Bitmap createBitmap(int width, int height, int padding) throws OutOfMemoryError {
        int newWidth = width + padding * 2;
        int newHeight = height + padding * 2;
        return createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
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
                throw new OutOfMemoryError("Cann't create editorImage for save");
            }
        }
        return bmp;
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

    private LongClickAsyncTask longClickAsyncTask;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int currentX = (int) event.getRawX();
        int currentY = (int) event.getRawY();
        if (!isEmpty()) {
            int touchCount = event.getPointerCount();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (drawingActivated) {
                        drawingStart(event.getX(), event.getY());
                        invalidate();
                        break;
                    }
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
                                editorImage.setLeft((int) (editorImage.getLeft() + (aB - ab)));
                                editorImage.setTop((int) (editorImage.getTop() + (aC - ac)));
                                mIndependentAngle = 0;
                                mIndependentScale = 1f;
                                fingersAngle = rotation(event);
                                float centerX = (event.getX(0) + event.getX(1)) / 2 + editorImage.getLeft();
                                float centerY = (event.getY(0) + event.getY(1)) / 2 + editorImage.getTop();
                                mRotateCenterAngle = Math.toDegrees(Math.atan(centerY / centerX));
                                mRotateCenterDistance = centerX / Math.cos(Math.toRadians(mRotateCenterAngle));
                            }
                        }
                        mPrevDistance = distance;
                        isScaling = true;
                        longClick = false;

                        // Todo: Draw sticker border this.

                    } else {
                        // Todo: drawTextBorder = true;
                        drawStickerBorder = true;
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
                        findCheckedText(mPrevMoveX, mPrevMoveY); // FIXME: Check it
                        findCheckedSticker(mPrevMoveX, mPrevMoveY);
                        longClick = true;
                        longClickAsyncTask = new LongClickAsyncTask(this);
                        longClickAsyncTask.execute(checkedTextId);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    imageCentering = true;
                    if (drawingActivated) {
                        drawingMove(event.getX(), event.getY());
                        invalidate();
                        break;
                    }
                    if (!isEmpty()) {
                        if (touchCount >= 2 && isScaling) {
                            float dist = distance(event.getX(0), event.getX(1), event.getY(0), event.getY(1));
                            float scale = (dist - mPrevDistance) / dispDistance();
                            mPrevDistance = dist;
                            scale += 1;
                            scale = scale * scale;
                            if (checkedTextId == -1) {
                                if (isFreeTransform()) {
                                    editorImage.setRoationDegrees(editorImage.getRoationDegrees() + fingersAngle - rotation(event));
                                    mIndependentAngle = mIndependentAngle + fingersAngle - rotation(event);
                                    fingersAngle = rotation(event);
                                    if (editorImage.getScale() * scale > 0 && editorImage.getScale() * scale < editorImage.getMaxScale()) {
                                        mIndependentScale = mIndependentScale * scale;
                                    }
                                    editorImage.setFreeScale(editorImage.getScale() * scale);
                                }
                            } else {
                                textsList.get(checkedTextId).setSize(textsList.get(checkedTextId).getSize() * scale);
                            }
                            if (checkedStickerId != -1) {
                                stickersList.get(checkedStickerId).setSize(stickersList.get(checkedStickerId).getSize() * scale);
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
                                    editorImage.setLeft(editorImage.getLeft() + Math.round(distanceX));
                                    editorImage.setTop(editorImage.getTop() + Math.round(distanceY));
                                    if (longClick && System.currentTimeMillis() > mLastTime + LONG_PRESS_MILLISECOND + 100) {
                                        if (onSquareEditorPictureClickListener != null) {
                                            longClick = false;
                                        }
                                    }
                                }
                            } else {
                                Text text = textsList.get(checkedTextId);
                                text.setX(text.getX() - Math.round(distanceX));
                                text.setY(text.getY() - Math.round(distanceY));
                                if (longClick && System.currentTimeMillis() > mLastTime + LONG_PRESS_TEXT_MILLISECOND) {
                                    if (onSquareEditorPictureClickListener != null) {
                                        longClick = false;
                                    }
                                }
                            }
                            if (checkedStickerId != -1) {
                                // FIXME: If sticker doesn't move.
                                Sticker sticker = stickersList.get(checkedStickerId);
                                sticker.setX(sticker.getX() - Math.round(distanceX));
                                sticker.setY(sticker.getY() - Math.round(distanceY));
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (drawingActivated) {
                        drawingStop();
                        invalidate();
                        break;
                    }
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
                                    //  editorImage.setBitmap(rotateImage(editorImage.getBitmap(), angle));
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
     * @param drawingPath - directory drawingPath to saving editorImage with padding and with text/
     * @return drawingPath to saved editorImage
     */
    /*public String[] saveImages(String drawingPath) throws IOException, OutOfMemoryError {
        if (!isEmpty()) {
            List<String> files = new LinkedList<String>();
            EditorImage editorImage = this.editorImage;
            long saveImagesTime = System.currentTimeMillis();
            String fileName = String.format("%d_editor.jpg", saveImagesTime);
            File file = new File(drawingPath, fileName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file, false);
                float pixelDensity = (float) (imagePart.width()) / (imageRect.width());
                Bitmap processedBitmap = createBitmap(imagePart.width(), imagePart.height(), (int) ((getImagePadding() * pixelDensity)));
                scalingForSave = (float) processedBitmap.getWidth() / imageArea.width();
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
    }*/

    /*public String[] saveImages(String drawingPath, String fileName) throws IOException, OutOfMemoryError {
        if (!isEmpty()) {
            List<String> files = new LinkedList<String>();
            EditorImage editorImage = this.editorImage;
            File file = new File(drawingPath, fileName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file, false);
                float pixelDensity = (float) (imagePart.width()) / (imageRect.width());
                Bitmap processedBitmap = createBitmap(imagePart.width(), imagePart.height(), (int) ((getImagePadding() * pixelDensity)));
                scalingForSave = (float) processedBitmap.getWidth() / imageArea.width();
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
    }*/

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

    public boolean isDeleteTextActivated() {
        return deleteTextActivated;
    }

    public void setDeleteTextActivated(boolean deleteTextActivated) {
        this.deleteTextActivated = deleteTextActivated;
    }

    public boolean isTextActivated() {
        return textActivated;
    }

    public void setTextActivated(boolean textActivated) {
        this.textActivated = textActivated;
    }

    // FIXME: Top meme text
    public void setTopMemeText(String topMemeText) {
        this.topMemeText = topMemeText.toUpperCase();
        invalidate();
        Log.i("Bottom meme text", topMemeText);
    }

    // FIXME: Bottom meme text
    public void setBottomMemeText(String bottomMemeText) {
        this.bottomMemeText = bottomMemeText.toUpperCase();
        invalidate();
        Log.i("Top meme text", bottomMemeText);
    }

    public interface OnSquareEditorListener {
        public void editText(Text giantSquareText);
    }

    public interface OnSquareEditorPictureClickListener {
        void onPictureLongClick();

        void onTextLongClick(int id);
    }

    private static class LongClickAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private final EditorView engine;

        private LongClickAsyncTask(EditorView engine) {
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

