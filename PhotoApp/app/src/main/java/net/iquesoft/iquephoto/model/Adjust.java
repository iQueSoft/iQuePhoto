package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

public class Adjust {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private int mValue;
    private boolean selected;

    public static List<Adjust> getAdjustList() {
        return Arrays.asList(adjusts);
    }

    private static Adjust[] adjusts = {
            new Adjust(R.string.brightness, R.drawable.ic_brightness, 0),
            new Adjust(R.string.contrast, R.drawable.ic_contrast, 0),
            new Adjust(R.string.saturation, R.drawable.ic_saturation, 0),
            new Adjust(R.string.warmth, R.drawable.ic_warmth, 0),
    };

    private Adjust(@StringRes int title, @DrawableRes int icon, int value) {
        mTitle = title;
        mIcon = icon;
        mValue = value;
    }

    public int getTitle() {
        return mTitle;
    }

    public int getIcon() {
        return mIcon;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
