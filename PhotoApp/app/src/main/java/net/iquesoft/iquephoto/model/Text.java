package net.iquesoft.iquephoto.model;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class Text {

    public static final float DEFAULT_SIZE = 100f;
    public static final int DEFAULT_COLOR = 0xFF000000;

    private String mText;
    private Typeface mTypeface;
    private String mTypefacePath;
    private float mSize;

    private int mColor;
    private int mX;
    private int mY;
    private int mOpacity = 255;

    private Paint mPaint;

    private Rect mRect;

    public Text(String text, Typeface typeface, int color, int opacity) {
        mText = text;
        mTypeface = typeface;
        mColor = color;
        mOpacity = opacity;
        mRect = new Rect();
        mPaint = new Paint();
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
        setRect();
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
        setRect();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        setRect();
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
        setRect();
    }

    public float getSize() {
        return mSize;
    }

    public void setSize(float size) {
        mSize = size;
        setRect();
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Text{");
        sb.append("mText='").append(mText).append('\'');
        sb.append(", mTypeface=").append(mTypeface);
        sb.append(", mSize=").append(mSize);
        sb.append(", mColor=").append(mColor);
        sb.append(", mX=").append(mX);
        sb.append(", mY=").append(mY);
        sb.append(", mRect=").append(mRect);
        sb.append('}');
        return sb.toString();
    }

    public Rect getRect() {
        return mRect;
    }

    private void setRect() {
        if (getText() != null) {
            getPaint().getTextBounds(getText(), 0, getText().length(), mRect);
            mRect.top += getSize() + getY();
            mRect.bottom += getSize() + getY();
            mRect.left += getX();
            mRect.right += getX();
        }
    }

    public String getTypefacePath() {
        return mTypefacePath;
    }

    public void setTypefacePath(String typefacePath) {
        mTypefacePath = typefacePath;
    }

    public int getOpacity() {
        return mOpacity;
    }

    public void setOpacity(int opacity) {
        mOpacity = opacity;
    }

    public Paint getPaint() {
        mPaint.setTypeface(mTypeface);
        mPaint.setAlpha(mOpacity);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mSize);

        return mPaint;
    }
}