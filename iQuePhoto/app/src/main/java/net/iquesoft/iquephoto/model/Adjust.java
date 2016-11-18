package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.EditorCommand;

import java.util.Arrays;
import java.util.List;

import static net.iquesoft.iquephoto.core.EditorCommand.*;

public class Adjust {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private EditorCommand mCommand;

    private int mValue;

    public static List<Adjust> getAdjustList() {
        return Arrays.asList(adjusts);
    }

    private static Adjust[] adjusts = {
            new Adjust(R.string.brightness, R.drawable.ic_brightness, BRIGHTNESS,
                    0),
            new Adjust(R.string.contrast, R.drawable.ic_contrast, CONTRAST,
                    0),
            new Adjust(R.string.saturation, R.drawable.ic_saturation, SATURATION,
                    0),
            new Adjust(R.string.warmth, R.drawable.ic_warmth, WARMTH,
                    0),
            new Adjust(R.string.tint, R.drawable.ic_fade, TINT, 0),

            new Adjust(R.string.exposure, R.drawable.ic_exposure, EXPOSURE,
                    0),
            new Adjust(R.string.fade, R.drawable.ic_fade, FADE, 0)

    };

    private Adjust(@StringRes int title, @DrawableRes int icon, @NonNull EditorCommand command,
                   int value) {
        mTitle = title;
        mIcon = icon;
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
}
