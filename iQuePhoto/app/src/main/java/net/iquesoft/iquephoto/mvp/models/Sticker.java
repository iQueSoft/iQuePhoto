package net.iquesoft.iquephoto.mvp.models;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

// TODO: Change sticker images size.
public class Sticker {
    @DrawableRes
    private int mImage;

    private Bitmap mBitmap;

    private static Sticker flagStickers[] = {
            new Sticker(R.drawable.flag_ukraine),
            new Sticker(R.drawable.flag_russia),
            new Sticker(R.drawable.flag_germany),
            new Sticker(R.drawable.flag_brazil)
    };

    private static Sticker emoticonsStickers[] = {
            new Sticker(R.drawable.emoticon_happy),
            new Sticker(R.drawable.emoticon_in_love),
            new Sticker(R.drawable.emoticon_smile),
            new Sticker(R.drawable.emoticon_tongue_out),
            new Sticker(R.drawable.emoticon_sad),
            new Sticker(R.drawable.emoticon_mad),
            new Sticker(R.drawable.emoticon_wink),
            new Sticker(R.drawable.emoticon_quiet),
            new Sticker(R.drawable.emoticon_unhappy),
            new Sticker(R.drawable.emoticon_bored),
            new Sticker(R.drawable.emoticon_ill),
            new Sticker(R.drawable.emoticon_nerd),
            new Sticker(R.drawable.emoticon_kissing),
            new Sticker(R.drawable.emoticon_embarrassed),
            new Sticker(R.drawable.emoticon_smart),
            new Sticker(R.drawable.emoticon_surprised)

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

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}



