package net.iquesoft.iquephoto.mvp.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.ui.fragments.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragments.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragments.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragments.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragments.FramesFragment;
import net.iquesoft.iquephoto.ui.fragments.OverlaysFragment;
import net.iquesoft.iquephoto.ui.fragments.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragments.StickersToolFragment;
import net.iquesoft.iquephoto.ui.fragments.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragments.ToolsFragment;

import java.util.Arrays;
import java.util.List;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.*;

public class Tool {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private Fragment mFragment;

    public static List<Tool> getToolsList() {
        return Arrays.asList(tools);
    }

    private static Tool tools[] = {
            new Tool(R.string.filters, R.drawable.ic_filter, FiltersFragment.newInstance()),
            new Tool(R.string.adjust, R.drawable.ic_adjust, new AdjustFragment()),
            new Tool(R.string.overlay, R.drawable.ic_overlay, new OverlaysFragment()),
            new Tool(R.string.stickers, R.drawable.ic_stiker, new StickersToolFragment()),
            new Tool(R.string.frames, R.drawable.ic_frame, new FramesFragment()),
            new Tool(R.string.transform, R.drawable.ic_frame, new ToolsFragment()),
            new Tool(R.string.vignette, R.drawable.ic_vignette, SliderControlFragment.newInstance(VIGNETTE)),
            new Tool(R.string.tilt_shift, R.drawable.ic_tilt_shift, new TiltShiftFragment()),
            new Tool(R.string.drawing, R.drawable.ic_brush, new DrawingFragment()),
            new Tool(R.string.text, R.drawable.ic_letters, new AddTextFragment())
    };

    private Tool(@StringRes int title, @DrawableRes int icon, @NonNull Fragment fragment) {
        mTitle = title;
        mIcon = icon;
        mFragment = fragment;
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

    public Fragment getFragment() {
        return mFragment;
    }
}
