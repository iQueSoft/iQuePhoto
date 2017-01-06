package net.iquesoft.iquephoto.models;

import android.support.annotation.DrawableRes;

public class Frame {
    private String mTitle;

    @DrawableRes
    private int mImage;

    public Frame(String title, @DrawableRes int image) {
        mTitle = title;
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getImage() {
        return mImage;
    }
}
