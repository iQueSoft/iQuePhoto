package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

public class StickersSet {
    @StringRes
    private int title;

    @DrawableRes
    private int icon;

    private List<Sticker> stickers;

    private StickersSet(@StringRes int title, @DrawableRes int icon, List<Sticker> stickers) {
        this.title = title;
        this.icon = icon;
        this.stickers = stickers;
    }

    /**
     * Array with stickers sets;
     */
    private static StickersSet stickersSets[] = {
            new StickersSet(R.string.emoticons, R.drawable.emoticon_happy, Sticker.getEmoticonsStickersList()),
            new StickersSet(R.string.flags, R.drawable.ic_flags, Sticker.getFlagStickersList())
    };

    /**
     * @return list with stickers sets;
     */
    public static List<StickersSet> getStickersSetsList() {
        return Arrays.asList(stickersSets);
    }

    public List<Sticker> getStickers() {
        return stickers;
    }

    public void setStickers(List<Sticker> stickers) {
        this.stickers = stickers;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
