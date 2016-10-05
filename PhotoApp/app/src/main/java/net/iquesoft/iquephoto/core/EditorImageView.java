package net.iquesoft.iquephoto.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class EditorImageView extends ImageView {
    private Bitmap mBitmap;
    private Bitmap mBitmapSource;

    private Canvas mCanvas;
    private Matrix mMatrix;
    private ColorMatrix mColorMatrix;
    private Paint mPaint;
    private Paint mFilterPaint;

    private boolean mIsInitialized;

    public boolean mHasFilter;

    public EditorImageView(Context context) {
        super(context);
    }

    public EditorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        mBitmapSource = bm;
        if (!mIsInitialized) {
            mBitmap = Bitmap.createBitmap(mBitmapSource.getWidth(), mBitmapSource.getHeight(), mBitmapSource.getConfig());
            mCanvas = new Canvas(mBitmap);
            mPaint = new Paint();
            mMatrix = new Matrix();
            mFilterPaint = new Paint();

            mIsInitialized = true;
        }

        mCanvas.drawBitmap(mBitmapSource, mMatrix, new Paint());

        super.setImageBitmap(bm);
    }

    public Bitmap getAlteredBitmap() {
        return mBitmap;
    }


    private void alterBitmap() {
        if (mHasFilter) {
            mFilterPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
            mCanvas.drawBitmap(mBitmap, mMatrix, mFilterPaint);
        }

        this.setImageBitmap(mBitmap);
    }

    public void setFilter(ColorMatrix colorMatrix) {
        if (colorMatrix != null) {
            mColorMatrix = colorMatrix;
            mHasFilter = true;
        } else mHasFilter = false;

        alterBitmap();
    }


}
