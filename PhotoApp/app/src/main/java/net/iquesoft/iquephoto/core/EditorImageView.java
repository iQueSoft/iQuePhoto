package net.iquesoft.iquephoto.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EditorImageView extends ImageView {

    private Bitmap mBitmap;
    private int mImageWidth;
    private int mImageHeight;

    private float brightnessValue = 0;

    private boolean hasFilter;
    private ColorMatrix filterMatrix;
    private Paint filterPaint;
    private ColorMatrixColorFilter colorMatrixColorFilter;

    public EditorImageView(Context context) {
        super(context);
    }

    public EditorImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        filterPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        if (hasFilter) {
            filterPaint.setColorFilter(colorMatrixColorFilter);
            canvas.drawBitmap(mBitmap, 0, 0, filterPaint);
        } else {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int imageWight = MeasureSpec.getSize(widthMeasureSpec);
        int imageHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(imageWight, imageHeight);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setFilterMatrix(ColorMatrix filterMatrix) {
        this.filterMatrix = filterMatrix;
        hasFilter = true;
        colorMatrixColorFilter = new ColorMatrixColorFilter(filterMatrix);
        invalidate();
    }

    public void setImageUri(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            float aspectRatio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            mImageWidth = displayMetrics.widthPixels;
            mImageHeight = Math.round(mImageWidth * aspectRatio);
            mBitmap = Bitmap.createScaledBitmap(bitmap, mImageWidth, mImageHeight, false);
            invalidate();
            requestLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public void setBrightnessValue(float brightnessValue) {
        this.brightnessValue = brightnessValue;
    }

    public void setHasNotFilter() {
        hasFilter = false;
        invalidate();
    }


}
