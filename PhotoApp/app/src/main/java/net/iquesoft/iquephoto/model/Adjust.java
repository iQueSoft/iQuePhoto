package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.view.fragment.BrightnessFragment;

import java.util.Arrays;
import java.util.List;

public class Adjust {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private int mValue;

    private Fragment mFragment;

    public static List<Adjust> getAdjustList() {
        return Arrays.asList(adjusts);
    }

    private static Adjust[] adjusts = {
            new Adjust(R.string.brightness, R.drawable.ic_brightness, new BrightnessFragment(), 0),
            new Adjust(R.string.contrast, R.drawable.ic_contrast, null, 0),
            new Adjust(R.string.saturation, R.drawable.ic_saturation, null, 0),
            new Adjust(R.string.warmth, R.drawable.ic_warmth, null, 0)
    };

    private Adjust(@StringRes int title, @DrawableRes int icon, @Nullable Fragment fragment, int value) {
        mTitle = title;
        mIcon = icon;
        mFragment = fragment;
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

    public Fragment getFragment() {
        return mFragment;
    }
}
