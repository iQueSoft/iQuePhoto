package net.iquesoft.iquephoto.model;

import android.support.annotation.ColorRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

public class EditorColor {
    @ColorRes
    private int mColor;

    private boolean mIsSelected;

    public static List<EditorColor> getColorsList() {
        return Arrays.asList(editorColors);
    }

    private static EditorColor[] editorColors = {
            new EditorColor(R.color.brown),
            new EditorColor(R.color.red),
            new EditorColor(R.color.crimson),
            new EditorColor(R.color.indian_red),
            new EditorColor(R.color.khaki),
            new EditorColor(R.color.yellow),
            new EditorColor(R.color.gold),
            new EditorColor(R.color.orange),
            new EditorColor(R.color.green_yellow),
            new EditorColor(R.color.spring_green),
            new EditorColor(R.color.lime),
            new EditorColor(R.color.olive_drab),
            new EditorColor(R.color.aqua),
            new EditorColor(R.color.sky_blue),
            new EditorColor(R.color.blue),
            new EditorColor(R.color.cyan),
            new EditorColor(R.color.magenta),
            new EditorColor(R.color.purple),
            new EditorColor(R.color.dark_violet),
            new EditorColor(R.color.indigo)

    };

    private EditorColor(@ColorRes int color) {
        mColor = color;
    }

    public EditorColor(@ColorRes int color, boolean isSelected) {
        mColor = color;
        mIsSelected = isSelected;
    }

    public int getColor() {
        return mColor;
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public boolean isSelected() {
        return mIsSelected;
    }
}
