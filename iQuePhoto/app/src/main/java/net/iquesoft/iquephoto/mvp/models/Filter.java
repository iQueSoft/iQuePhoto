package net.iquesoft.iquephoto.mvp.models;

import android.graphics.ColorMatrix;

public class Filter {
    private String mTitle;
    private ColorMatrix mColorMatrix;

    public Filter(String title, ColorMatrix colorMatrix) {
        mTitle = title;
        mColorMatrix = colorMatrix;
    }

    public String getTitle() {
        return mTitle;
    }

    public ColorMatrix getColorMatrix() {
        return mColorMatrix;
    }
}
