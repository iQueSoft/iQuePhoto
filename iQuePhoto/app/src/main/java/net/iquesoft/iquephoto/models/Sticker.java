package net.iquesoft.iquephoto.models;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

public class Sticker {
    @DrawableRes
    private int mImage;

    private Bitmap mBitmap;

    private static Sticker flagStickers[] = {
            new Sticker(R.drawable.s_flag_01),
            new Sticker(R.drawable.s_flag_02),
            new Sticker(R.drawable.s_flag_03),
            new Sticker(R.drawable.s_flag_04),
            new Sticker(R.drawable.s_flag_05),
            new Sticker(R.drawable.s_flag_06),
    };

    private static Sticker emoticonsStickers[] = {
            new Sticker(R.drawable.s_emoji_01),
            new Sticker(R.drawable.s_emoji_02),
            new Sticker(R.drawable.s_emoji_03),
            new Sticker(R.drawable.s_emoji_04),
            new Sticker(R.drawable.s_emoji_05),
            new Sticker(R.drawable.s_emoji_06),
            new Sticker(R.drawable.s_emoji_07),
    };

    private static Sticker christmasStickers[] = {
            new Sticker(R.drawable.s_christmas_01),
            new Sticker(R.drawable.s_christmas_02),
            new Sticker(R.drawable.s_christmas_03),
            new Sticker(R.drawable.s_christmas_04),
            new Sticker(R.drawable.s_christmas_05),
            new Sticker(R.drawable.s_christmas_06),
            new Sticker(R.drawable.s_christmas_07),
            new Sticker(R.drawable.s_christmas_08),
            new Sticker(R.drawable.s_christmas_09),
            new Sticker(R.drawable.s_christmas_10),
            new Sticker(R.drawable.s_christmas_11),
            new Sticker(R.drawable.s_christmas_12),
            new Sticker(R.drawable.s_christmas_13),
            new Sticker(R.drawable.s_christmas_14)
    };

    private static Sticker halloweenStickers[] = {
            new Sticker(R.drawable.s_halloween_01),
            new Sticker(R.drawable.s_halloween_02),
            new Sticker(R.drawable.s_halloween_03),
    };

    private static Sticker warningStickers[] = {
            new Sticker(R.drawable.s_warning_01),
            new Sticker(R.drawable.s_warning_02),
    };

    private static Sticker valentinesDayStickers[] = {
            new Sticker(R.drawable.s_valentines_day_01),
            new Sticker(R.drawable.s_valentines_day_02),
            new Sticker(R.drawable.s_valentines_day_03),
            new Sticker(R.drawable.s_valentines_day_04),
    };

    private Sticker(@DrawableRes int image) {
        mImage = image;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    public static List<Sticker> getFlagStickersList() {
        return Arrays.asList(flagStickers);
    }

    public static List<Sticker> getEmoticonsStickersList() {
        return Arrays.asList(emoticonsStickers);
    }

    public static List<Sticker> getChristmasStickersList() {
        return Arrays.asList(christmasStickers);
    }

    public static List<Sticker> getWarningStickersList() {
        return Arrays.asList(warningStickers);
    }

    public static List<Sticker> getHalloweenStickersList() {
        return Arrays.asList(halloweenStickers);
    }

    public static List<Sticker> getValentinesDayStickersList() {
        return Arrays.asList(valentinesDayStickers);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}



