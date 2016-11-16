package net.iquesoft.iquephoto.model;

import java.util.Arrays;
import java.util.List;

public class Font {
    private String mTitle;
    private String mTypeface;
    private boolean mIsSelected;

    public static List<Font> getFontsList() {
        return Arrays.asList(fonts);
    }

    private static Font fonts[] = {
            new Font("Black Sword", "Blacksword.otf"),
            new Font("Summer Hearts", "SummerHearts-Regular.otf"),
            new Font("Cigarettes & Coffee", "CigarettesAndCoffee.ttf"),
            new Font("Abys", "Abys-Regular.otf"),
            new Font("Reis", "REIS-Regular.ttf"),
    };

    public Font(String title, String typeface) {
        mTitle = title;
        mTypeface = typeface;
    }
    
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTypeface() {
        return "fonts/" + mTypeface;
    }

    public void setTypeface(String typeface) {
        mTypeface = typeface;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }
}

