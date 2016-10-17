package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

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

    private static Overlay[] overlays = {new Overlay("COLOR01", R.drawable.overlay_color_1),
            new Overlay("COLOR02", R.drawable.ovarlay_color_02),
            new Overlay("COLOR03", R.drawable.overlay_color_03),
            new Overlay("SKY01", R.drawable.overlay_sky),
            new Overlay("INK01", R.drawable.overlay_ink_01),
            new Overlay("DUST01", R.drawable.dust_1)};

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
