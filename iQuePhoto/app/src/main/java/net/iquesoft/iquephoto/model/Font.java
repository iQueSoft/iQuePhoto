package net.iquesoft.iquephoto.model;

import java.util.Arrays;
import java.util.List;

public class Font {
    private String mTitle;
    private String mPath;

    public static List<Font> getFontsList() {
        return Arrays.asList(fonts);
    }

    private static Font fonts[] = {
            new Font("Souses", "Souses.otf"),
            new Font("Black Sword", "Blacksword.otf"),
            new Font("Summer Hearts", "SummerHearts-Regular.otf"),
            new Font("Cigarettes & Coffee", "CigarettesAndCoffee.ttf"),
            new Font("Abys", "Abys-Regular.otf"),
            new Font("Reis", "REIS-Regular.ttf"),
    };

    public Font(String title, String path) {
        mTitle = title;
        mPath = path;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPath() {
        return "fonts/" + mPath;
    }
}

