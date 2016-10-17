package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.view.fragment.DrawingFragment;
import net.iquesoft.iquephoto.view.fragment.AdjustFragment;
import net.iquesoft.iquephoto.view.fragment.FiltersFragment;
import net.iquesoft.iquephoto.view.fragment.OverlayFragment;
import net.iquesoft.iquephoto.view.fragment.StickersFragment;
import net.iquesoft.iquephoto.view.fragment.TextFragment;

import java.util.Arrays;
import java.util.List;

public class Tool {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private Fragment fragment;

    public static List<Tool> getToolsList() {
        return Arrays.asList(tools);
    }

    private static Tool tools[] = {
            new Tool(R.string.filters, R.drawable.ic_filter, new FiltersFragment()),
            new Tool(R.string.adjust, R.drawable.ic_adjust, AdjustFragment.newInstance()),
            new Tool(R.string.overlay, R.drawable.ic_overlay, new OverlayFragment()),
            new Tool(R.string.stickers, R.drawable.ic_stiker, StickersFragment.newInstance()),
            new Tool(R.string.frame, R.drawable.ic_frame),
            new Tool(R.string.drawing, R.drawable.ic_brush, DrawingFragment.newInstance()),
            new Tool(R.string.text, R.drawable.ic_letters, TextFragment.newInstance())
    };

    private static Tool tool = new Tool();

    public static Tool getInstance() {
        return tool;
    }

    private Tool() {

    }

    private Tool(@StringRes int title, @DrawableRes int icon) {
        mTitle = title;
        mIcon = icon;
    }

    private Tool(@StringRes int title, @DrawableRes int icon, @Nullable Fragment fragment) {
        mTitle = title;
        mIcon = icon;
        this.fragment = fragment;
    }

    public int getIcon() {
        return mIcon;
    }

    public int getTitle() {
        return mTitle;
    }

    public void setTitle(int title) {
        mTitle = title;
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
