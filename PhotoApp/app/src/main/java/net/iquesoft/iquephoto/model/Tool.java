package net.iquesoft.iquephoto.model;

import android.app.Fragment;
import android.support.annotation.Nullable;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.view.fragment.BrashFragment;
import net.iquesoft.iquephoto.view.fragment.BrightnessFragment;
import net.iquesoft.iquephoto.view.fragment.FiltersFragment;
import net.iquesoft.iquephoto.view.fragment.RotationFragment;
import net.iquesoft.iquephoto.view.fragment.TextFragment;

import java.util.Arrays;
import java.util.List;

/**
 * @author Sergey Belenkiy
 *         Tool.java - Singleton class is model of editing tools in app.
 */
public class Tool {
    private int title;
    private int image;
    private Fragment fragment;

    /**
     * @return list with all editor tools for adapter usage;
     */
    public static List<Tool> getToolsList() {
        return Arrays.asList(tools);
    }

    /**
     * Array with all app editors tools;
     */
    public static Tool tools[] = {
            new Tool(R.string.tool_filters, R.drawable.ic_filter, new FiltersFragment()),
            new Tool(R.string.tool_stickers, R.drawable.ic_stiker),
            new Tool(R.string.tool_frame, R.drawable.ic_frame),
            new Tool(R.string.tool_crop, R.drawable.ic_crop),
            new Tool(R.string.tool_rotate, R.drawable.ic_rotation, RotationFragment.newInstance()),
            new Tool(R.string.brightness, R.drawable.ic_brightness, BrightnessFragment.newInstance()),
            new Tool(R.string.brush, R.drawable.ic_brush, BrashFragment.newInstance()),
            new Tool(R.string.tool_text, R.drawable.ic_letters, TextFragment.newInstance()),
    };

    private static Tool tool = new Tool();

    public static Tool getInstance() {
        return tool;
    }

    public Tool() {

    }

    /**
     * @param title is string Id from strings.xml;
     * @param image is drawable Id form res/drawable.
     */
    public Tool(int title, int image) {
        this.title = title;
        this.image = image;
    }

    /**
     * @param title    is string Id from strings.xml;
     * @param image    is drawable Id form res/drawable;
     * @param fragment it is Fragment used for control this tools;
     */
    public Tool(int title, int image, @Nullable Fragment fragment) {
        this.title = title;
        this.image = image;
        this.fragment = fragment;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    /**
     * @return fragment of control selected tool;
     */
    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
