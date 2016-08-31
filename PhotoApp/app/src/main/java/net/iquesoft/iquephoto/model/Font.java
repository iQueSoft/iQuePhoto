package net.iquesoft.iquephoto.model;

import java.util.Arrays;
import java.util.List;

/**
 * @author Sergey
 */
public class Font {

    private String title;
    private String typeface;

    /**
     * @return list with all fonts for adapter usage;
     */
    public static List<Font> getFontsList() {
        return Arrays.asList(fonts);
    }

    /**
     * Array with all fonts;
     */
    public static Font fonts[] = {
            new Font("Black sword", "Blacksword.otf"),
            new Font("Summer Hearts", "SummerHearts-Regular.otf"),
            new Font("Abys", "Abys-Regular.otf"),
            new Font("Reis", "REIS-Regular.ttf"),
    };

    /**
     * @param title    is font title;
     * @param typeface is string of font path.
     */
    public Font(String title, String typeface) {
        this.title = title;
        this.typeface = typeface;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeface() {
        return typeface;
    }

    public void setTypeface(String typeface) {
        this.typeface = typeface;
    }
}

