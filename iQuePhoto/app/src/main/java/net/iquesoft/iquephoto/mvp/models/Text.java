package net.iquesoft.iquephoto.mvp.models;

import android.graphics.Typeface;

public class Text {
    private String mText;
    private int mOpacity;
    private Typeface mTypeface;
    private int mColor;

    public Text(String text, Typeface typeface, int color, int opacity) {
        mText = text;
        mTypeface = typeface;
        mColor = color;
        mOpacity = opacity;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getOpacity() {
        return mOpacity;
    }

    public void setOpacity(int opacity) {
        mOpacity = opacity;
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
}
