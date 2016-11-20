package net.iquesoft.iquephoto.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.ui.fragment.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragment.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragment.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragment.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.FramesFragment;
import net.iquesoft.iquephoto.ui.fragment.OverlayFragment;
import net.iquesoft.iquephoto.ui.fragment.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragment.StickersFragment;
import net.iquesoft.iquephoto.ui.fragment.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformFragment;

import java.util.Arrays;
import java.util.List;

import static net.iquesoft.iquephoto.core.EditorCommand.*;

public class Tool {
    @StringRes
    private int mTitle;

    @DrawableRes
    private int mIcon;

    private EditorCommand mCommand;

    public static List<Tool> getToolsList() {
        return Arrays.asList(tools);
    }

    private static Tool tools[] = {
            new Tool(R.string.filters, R.drawable.ic_filter, FILTERS),
            new Tool(R.string.adjust, R.drawable.ic_adjust, ADJUST),
            new Tool(R.string.overlay, R.drawable.ic_overlay, OVERLAY),
            new Tool(R.string.stickers, R.drawable.ic_stiker, STICKERS),
            new Tool(R.string.frames, R.drawable.ic_frame, FRAMES),
            new Tool(R.string.transform, R.drawable.ic_frame, TRANSFORM),
            new Tool(R.string.vignette, R.drawable.ic_vignette, VIGNETTE),
            new Tool(R.string.tilt_shift, R.drawable.ic_tilt_shift, TILT_SHIFT),
            new Tool(R.string.drawing, R.drawable.ic_brush, DRAWING),
            new Tool(R.string.text, R.drawable.ic_letters, TEXT)
    };

    private Tool(@StringRes int title, @DrawableRes int icon, @NonNull EditorCommand command) {
        mTitle = title;
        mIcon = icon;
        mCommand = command;
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

    public EditorCommand getCommand() {
        return mCommand;
    }
}
