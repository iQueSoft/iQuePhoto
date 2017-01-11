package net.iquesoft.iquephoto.models;

import android.graphics.Typeface;

public class Text {
    private String mText;
    private Typeface mTypeface;
    private int mColor;

    public Text(String text, Typeface typeface, int color) {
        mText = text;
        mTypeface = typeface;
        mColor = color;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
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
