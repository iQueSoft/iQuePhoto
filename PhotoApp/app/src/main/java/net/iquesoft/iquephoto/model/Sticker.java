package net.iquesoft.iquephoto.model;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

public class Sticker {

    @StringRes
    private int mTitle;

    private int mImage;

    private Bitmap mBitmap;

    private float mScale;

    private float mSize = 100;

    private float mX = 0;
    private float mY = 0;

    private static Sticker flagStickers[] = {
            new Sticker(R.string.flag_ukraine, R.drawable.flag_ukraine),
            new Sticker(R.string.flag_russia, R.drawable.flag_russia),
            new Sticker(R.string.flag_germany, R.drawable.flag_germany),
            new Sticker(R.string.flag_brazil, R.drawable.flag_brazil)
    };

    private static Sticker emoticonsStickers[] = {
            new Sticker(R.string.emoticon_happy, R.drawable.emoticon_happy),
            new Sticker(R.string.emoticon_in_love, R.drawable.emoticon_in_love),
            new Sticker(R.string.emoticon_smile, R.drawable.emoticon_smile),
            new Sticker(R.string.emoticon_tongue_out, R.drawable.emoticon_tongue_out),
            new Sticker(R.string.emoticon_sad, R.drawable.emoticon_sad),
            new Sticker(R.string.emoticon_mad, R.drawable.emoticon_mad),
            new Sticker(R.string.emoticon_wink, R.drawable.emoticon_wink),
            new Sticker(R.string.emoticon_quiet, R.drawable.emoticon_quiet),
            new Sticker(R.string.emoticon_unhappy, R.drawable.emoticon_unhappy),
            new Sticker(R.string.emoticon_bored, R.drawable.emoticon_bored),
            new Sticker(R.string.emoticon_ill, R.drawable.emoticon_ill),
            new Sticker(R.string.emoticon_nerd, R.drawable.emoticon_nerd),
            new Sticker(R.string.emoticon_kissing, R.drawable.emoticon_kissing),
            new Sticker(R.string.emoticon_embarrassed, R.drawable.emoticon_embarrassed),
            new Sticker(R.string.emoticon_smart, R.drawable.emoticon_smart),
            new Sticker(R.string.emoticon_surprised, R.drawable.emoticon_surprised)

    };

    private Sticker(@StringRes int title, @DrawableRes int image) {
        mTitle = title;
        mImage = image;
    }

    public int getTitle() {
        return mTitle;
    }

    public void setTitle(int title) {
        mTitle = title;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    /**
     * @return list with "Flag" stickers;
     */
    public static List<Sticker> getFlagStickersList() {
        return Arrays.asList(flagStickers);
    }

    /**
     * @return list with "Emoticons" stickers;
     */
    public static List<Sticker> getEmoticonsStickersList() {
        return Arrays.asList(emoticonsStickers);
    }

    public float getX() {
        return mX;
    }

    public void setX(float x) {
        mX = x;
    }

    public float getY() {
        return mY;
    }

    public void setY(float y) {
        mY = y;
    }

    public float getSize() {
        return mSize;
    }

    public void setSize(float size) {
        mSize = size;
        //setStickerArea();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float scale) {
        mScale = scale;
    }
}

    /*public void setStickerArea(Rect stickerArea) {
        if (getText() != null && typeface != null) {
            Paint paint = new Paint();
            setPaintParams(paint);
            paint.getTextBounds(getText(), 0, getText().length(), textArea);
            textArea.top += getSize() + getY() - STICKER_AREA_MARGIN;
            textArea.bottom += getSize() + getY() + STICKER_AREA_MARGIN;
            textArea.left += getX() - STICKER_AREA_MARGIN;
            textArea.right += getX() + STICKER_AREA_MARGIN;
        }
    }*/

