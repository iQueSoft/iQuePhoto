package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

/**
 * @see net.iquesoft.iquephoto.view.fragment.AdjustFragment
 * @see net.iquesoft.iquephoto.adapters.AdjustAdapter
 */
public class Adjust {
    @StringRes
    private int title;

    @DrawableRes
    private int icon;

    private int value;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
