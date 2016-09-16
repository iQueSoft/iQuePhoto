package net.iquesoft.iquephoto.model;

import net.iquesoft.iquephoto.R;

import java.lang.reflect.Array;
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
            new EditorColor(R.color.red),
            new EditorColor(R.color.lime),
            new EditorColor(R.color.blue),
            new EditorColor(R.color.aqua),
            new EditorColor(R.color.magenta),
            new EditorColor(R.color.purple),

    };

    public EditorColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
