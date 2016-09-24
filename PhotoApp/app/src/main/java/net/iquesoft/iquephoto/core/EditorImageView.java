package net.iquesoft.iquephoto.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class EditorImageView extends ImageView {

    private Bitmap bitmap;

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
        if (hasFilter) {
            filterPaint.setColorFilter(colorMatrixColorFilter);
            Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
            canvas.drawBitmap(bitmap, getLeft(), getTop(), filterPaint);
        }
    }

    public void setFilterMatrix(ColorMatrix filterMatrix) {
        this.filterMatrix = filterMatrix;
        hasFilter = true;
        colorMatrixColorFilter = new ColorMatrixColorFilter(filterMatrix);
        invalidate();
    }

    /*public Bitmap getBitmap() {
        return bitmap;
    }*/

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setBrightnessValue(float brightnessValue) {
        this.brightnessValue = brightnessValue;
    }

    public void setHasNotFilter() {
        hasFilter = false;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
