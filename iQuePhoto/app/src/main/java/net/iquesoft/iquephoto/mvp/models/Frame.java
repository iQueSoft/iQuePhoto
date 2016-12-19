package net.iquesoft.iquephoto.mvp.models;

import android.support.annotation.DrawableRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

public class Frame {
    private String mTitle;

    @DrawableRes
    private int mImage;

    public static List<Frame> getFramesList() {
        return Arrays.asList(frames);
    }

    private static Frame[] frames = {
            new Frame("GRUNGE01", R.drawable.frame_grunge_01),
            new Frame("GRUNGE02", R.drawable.frame_grunge_02),
            new Frame("GRUNGE03", R.drawable.frame_grunge_03),
            new Frame("GRUNGE04", R.drawable.frame_grunge_04),
            new Frame("HL01", R.drawable.frame_h_01)
    };
    
    private Frame(String title, @DrawableRes int image) {
        mTitle = title;
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getImage() {
        return mImage;
    }
}
