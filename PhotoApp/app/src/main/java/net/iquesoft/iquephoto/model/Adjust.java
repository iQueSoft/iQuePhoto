package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;

/**
 * Created by Sergey on 9/22/2016.
 */
class Adjust {
    @StringRes
    private int title;

    @DrawableRes
    private int icon;

    private int value;

    public static Adjust[] adjusts = {
            new Adjust(R.string.contrast, R.drawable.ic_contrast, 0),
    };

    private Adjust() {

    }

    private Adjust(@StringRes int title, @DrawableRes int icon, int value) {
        this.title = title;
        this.icon = icon;
        this.value = value;
    }

    public int getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
