package net.iquesoft.iquephoto.models;

import android.support.annotation.DrawableRes;

public class Overlay {
    private String mTitle;

    @DrawableRes
    private int mImage;

    public Overlay(String title, @DrawableRes int image) {
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
