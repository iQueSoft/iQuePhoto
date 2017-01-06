package net.iquesoft.iquephoto.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.util.List;

public class StickersSet {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private List<Sticker> mStickers;

    public StickersSet(@StringRes int title, @DrawableRes int icon, List<Sticker> stickers) {
        mTitle = title;
        mIcon = icon;
        mStickers = stickers;
    }

    public List<Sticker> getStickers() {
        return mStickers;
    }

    public int getTitle() {
        return mTitle;
    }

    public int getIcon() {
        return mIcon;
    }
}
