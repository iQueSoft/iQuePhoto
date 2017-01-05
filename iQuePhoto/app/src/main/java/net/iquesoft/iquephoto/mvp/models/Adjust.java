package net.iquesoft.iquephoto.mvp.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.enums.EditorTool;

import java.util.Arrays;
import java.util.List;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.*;

public class Adjust {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private int mValue;
    private int mMinValue;
    private int mMaxValue;

    private EditorTool mCommand;

    public Adjust(@StringRes int title, @DrawableRes int icon, @NonNull EditorTool command,
                  int minValue, int maxValue, int value) {
        mTitle = title;
        mIcon = icon;
        mCommand = command;
        mMinValue = minValue;
        mMaxValue = maxValue;
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

    public int getMaxValue() {
        return mMaxValue;
    }

    public int getMinValue() {
        return mMinValue;
    }

    public EditorTool getCommand() {
        return mCommand;
    }
}
