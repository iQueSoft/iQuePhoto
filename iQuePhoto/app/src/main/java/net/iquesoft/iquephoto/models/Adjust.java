package net.iquesoft.iquephoto.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

public class Adjust {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private Fragment mFragment;

    public Adjust(@StringRes int title, @DrawableRes int icon, @NonNull Fragment fragment) {
        mTitle = title;
        mIcon = icon;
        mFragment = fragment;
    }

    public int getTitle() {
        return mTitle;
    }

    public int getIcon() {
        return mIcon;
    }

    public Fragment getFragment() {
        return mFragment;
    }
}