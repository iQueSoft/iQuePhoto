package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.view.fragment.DrawingFragment;
import net.iquesoft.iquephoto.view.fragment.AdjustFragment;
import net.iquesoft.iquephoto.view.fragment.FiltersFragment;
import net.iquesoft.iquephoto.view.fragment.FramesFragment;
import net.iquesoft.iquephoto.view.fragment.OverlayFragment;
import net.iquesoft.iquephoto.view.fragment.SliderControlFragment;
import net.iquesoft.iquephoto.view.fragment.StickersFragment;
import net.iquesoft.iquephoto.view.fragment.TextFragment;
import net.iquesoft.iquephoto.view.fragment.TiltShiftFragment;
import net.iquesoft.iquephoto.view.fragment.TransformFragment;

import java.util.Arrays;
import java.util.List;

import static net.iquesoft.iquephoto.core.EditorCommand.*;

public class Tool {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private EditorCommand mCommand;

    private Fragment mFragment;

    public static List<Tool> getToolsList() {
        return Arrays.asList(tools);
    }

    private static Tool tools[] = {
            new Tool(R.string.filters, R.drawable.ic_filter, FILTERS, new FiltersFragment()),
            new Tool(R.string.adjust, R.drawable.ic_adjust, ADJUST, new AdjustFragment()),
            new Tool(R.string.overlay, R.drawable.ic_overlay, OVERLAY, new OverlayFragment()),
            new Tool(R.string.stickers, R.drawable.ic_stiker, STICKERS, StickersFragment.newInstance()),
            new Tool(R.string.frames, R.drawable.ic_frame, FRAMES, new FramesFragment()),
            new Tool(R.string.transform, R.drawable.ic_frame, TRANSFORM, new TransformFragment()),
            new Tool(R.string.vignette, R.drawable.ic_vignette, VIGNETTE, SliderControlFragment.newInstance(VIGNETTE)),
            new Tool(R.string.tilt_shift, R.drawable.ic_tilt_shift, TILT_SHIFT, new TiltShiftFragment()),
            new Tool(R.string.drawing, R.drawable.ic_brush, DRAWING, DrawingFragment.newInstance()),
            new Tool(R.string.text, R.drawable.ic_letters, TEXT, TextFragment.newInstance())
    };

    private Tool(@StringRes int title, @DrawableRes int icon, @NonNull EditorCommand command, @Nullable Fragment fragment) {
        mTitle = title;
        mIcon = icon;
        mCommand = command;
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

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public EditorCommand getCommand() {
        return mCommand;
    }
}
