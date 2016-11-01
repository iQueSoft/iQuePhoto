package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

public class Overlay {

    private String mTitle;

    @DrawableRes
    private int mImage;

    private boolean mIsSelected;

    public static List<Overlay> getOverlaysList() {
        return Arrays.asList(overlays);
    }

    private static Overlay[] overlays = {
            new Overlay("C01", R.drawable.overlay_color_1),
            new Overlay("C02", R.drawable.ovarlay_color_02),
            new Overlay("C03", R.drawable.overlay_color_03),
            new Overlay("C04", R.drawable.overlay_color_04),
            new Overlay("C05", R.drawable.overlay_color_05),
            new Overlay("C6", R.drawable.overlay_color_06),
            new Overlay("C07", R.drawable.overlay_color_07),
            new Overlay("D01", R.drawable.overlay_dust_02),
            new Overlay("D02", R.drawable.overlay_dust_03),
            new Overlay("FD01", R.drawable.overlay_fd_01),
            new Overlay("FD02", R.drawable.overlay_fd_02)};

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

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }
}
