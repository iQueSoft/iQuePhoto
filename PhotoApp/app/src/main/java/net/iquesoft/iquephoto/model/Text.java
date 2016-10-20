package net.iquesoft.iquephoto.model;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class Text {

    public static final int TEXT_AREA_MARGIN = 7;
    public static final int TEXT_BACKGROUND_COLOR = Color.parseColor("#80404040");

    private String mText;
    private Typeface mTypeface;
    private String mTypefacePath;
    private float mSize = 120f;

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
        setTextArea();
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
        setTextArea();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        setTextArea();
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
        setTextArea();
    }

    public float getSize() {
        return mSize;
    }

    public void setSize(float size) {
        mSize = size;
        setTextArea();
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
        sb.append(", mTypefacePath='").append(mTypefacePath).append('\'');
        sb.append(", mSize=").append(mSize);
        sb.append(", mColor=").append(mColor);
        sb.append(", mX=").append(mX);
        sb.append(", mY=").append(mY);
        sb.append(", mRect=").append(mRect);
        sb.append('}');
        return sb.toString();
    }

    public Rect getTextArea() {
        return mRect;
    }

    private void setTextArea() {
        if (getText() != null && mTypeface != null) {
            Paint paint = new Paint();
            setPaintParams(paint);
            paint.getTextBounds(getText(), 0, getText().length(), mRect);
            mRect.top += getSize() + getY() - TEXT_AREA_MARGIN;
            mRect.bottom += getSize() + getY() + TEXT_AREA_MARGIN;
            mRect.left += getX() - TEXT_AREA_MARGIN;
            mRect.right += getX() + TEXT_AREA_MARGIN;
        }
    }

    public void setPaintParams(Paint paint) {
        paint.setColor(getColor());
        paint.setTypeface(getTypeface());
        paint.setAntiAlias(true);
        paint.setTextSize(getSize());
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

        return mPaint;
    }
}