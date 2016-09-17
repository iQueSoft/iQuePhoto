package net.iquesoft.iquephoto.model;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey on 9/16/2016.
 */
public class EditorColor {
    private int color;
    private boolean selected;

    public static List<EditorColor> getColorsList() {
        return Arrays.asList(editorColors);
    }

    public static EditorColor[] editorColors = {
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

    public EditorColor(int color) {
        this.color = color;
    }

    public EditorColor(int color, boolean selected) {
        this.color = color;
        this.selected = selected;
    }

    public int getColor() {
        return color;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
