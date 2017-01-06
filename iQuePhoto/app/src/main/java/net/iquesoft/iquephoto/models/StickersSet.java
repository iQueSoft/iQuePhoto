package net.iquesoft.iquephoto.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

public class StickersSet {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private List<Sticker> mStickers;
    
    private StickersSet(@StringRes int title, @DrawableRes int icon, List<Sticker> stickers) {
        mTitle = title;
        mIcon = icon;
        mStickers = stickers;
    }

    /**
     * Array with mStickers sets;
     */
    private static StickersSet stickersSets[] = {
            new StickersSet(R.string.emoticons, R.drawable.s_emoji_01, Sticker.getEmoticonsStickersList()),
            new StickersSet(R.string.flags, R.drawable.ic_flags, Sticker.getFlagStickersList()),
            new StickersSet(R.string.halloween, R.drawable.s_halloween_01, Sticker.getHalloweenStickersList()),
            new StickersSet(R.string.christmas, R.drawable.s_christmas_01, Sticker.getChristmasStickersList()),
            new StickersSet(R.string.valentines_day, R.drawable.s_valentines_day_01, Sticker.getValentinesDayStickersList()),
            new StickersSet(R.string.warnings, R.drawable.s_warning_01, Sticker.getWarningStickersList()),
    };

    /**
     * @return list with mStickers sets;
     */
    public static List<StickersSet> getStickersSetsList() {
        return Arrays.asList(stickersSets);
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
