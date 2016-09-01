package net.iquesoft.iquephoto.model;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey on 9/1/2016.
 */
public class StickersSet {

    private int title;
    private int icon;
    private List<Sticker> stickers;

    public StickersSet(int title, int icon, List<Sticker> stickers) {
        this.title = title;
        this.icon = icon;
        this.stickers = stickers;
    }

    /**
     * Array with stickers sets;
     */
    public static StickersSet stickersSets[] = {
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
