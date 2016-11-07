package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.view.fragment.BrightnessFragment;

import java.util.Arrays;
import java.util.List;

public class Adjust {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private EditorCommand mCommand;

    private int mValue;

    private Fragment mFragment;

    public static List<Adjust> getAdjustList() {
        return Arrays.asList(adjusts);
    }

    private static Adjust[] adjusts = {
            new Adjust(R.string.brightness, R.drawable.ic_brightness, EditorCommand.BRIGHTNESS,
                    new BrightnessFragment(), 0),
            new Adjust(R.string.contrast, R.drawable.ic_contrast, EditorCommand.CONTRAST,
                    null, 0),
            new Adjust(R.string.saturation, R.drawable.ic_saturation, EditorCommand.SATURATION,
                    null, 0),
            new Adjust(R.string.warmth, R.drawable.ic_warmth, EditorCommand.WARMTH,
                    null, 0)
    };

    private Adjust(@StringRes int title, @DrawableRes int icon, @NonNull EditorCommand command,
                   @Nullable Fragment fragment, int value) {
        mTitle = title;
        mIcon = icon;
        mFragment = fragment;
        mCommand = command;
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

    public EditorCommand getCommand() {
        return mCommand;
    }

    public Fragment getFragment() {
        return mFragment;
    }
}
