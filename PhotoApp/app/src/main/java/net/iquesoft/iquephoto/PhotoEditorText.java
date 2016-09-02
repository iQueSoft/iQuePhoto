package net.iquesoft.iquephoto;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class PhotoEditorText {

    public static final int TEXT_AREA_MARGIN = 7;
    public static final int TEXT_BACKGROUND_COLOR = Color.parseColor("#80404040");
    private String text;
    private Typeface typeface;
    private String typefacePath;
    private float size;
    private int color;
    private int x;
    private int y;
    private int opacity;
    private Rect textArea = new Rect();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        setTextArea();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        setTextArea();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        setTextArea();
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        setTextArea();
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
        setTextArea();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GiantSquareText{");
        sb.append("text='").append(text).append('\'');
        sb.append(", typeface=").append(typeface);
        sb.append(", typefacePath='").append(typefacePath).append('\'');
        sb.append(", size=").append(size);
        sb.append(", color=").append(color);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", textArea=").append(textArea);
        sb.append('}');
        return sb.toString();
    }

    public Rect getTextArea() {
        return textArea;
    }

    private void setTextArea() {
        if (getText() != null && typeface != null) {
            Paint paint = new Paint();
            setPaintParams(paint);
            paint.getTextBounds(getText(), 0, getText().length(), textArea);
            textArea.top += getSize() + getY() - TEXT_AREA_MARGIN;
            textArea.bottom += getSize() + getY() + TEXT_AREA_MARGIN;
            textArea.left += getX() - TEXT_AREA_MARGIN;
            textArea.right += getX() + TEXT_AREA_MARGIN;
        }
    }

    public void setPaintParams(Paint paint) {
        paint.setColor(getColor());
        paint.setTypeface(getTypeface());
        paint.setAntiAlias(true);
        paint.setTextSize(getSize());
    }

    public String getTypefacePath() {
        return typefacePath;
    }

    public void setTypefacePath(String typefacePath) {
        this.typefacePath = typefacePath;
    }

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }
}