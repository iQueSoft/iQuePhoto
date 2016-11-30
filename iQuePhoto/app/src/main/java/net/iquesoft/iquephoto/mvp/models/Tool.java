package net.iquesoft.iquephoto.mvp.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;

import java.util.Arrays;
import java.util.List;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.*;

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
            new Tool(R.string.tilt_shift, R.drawable.ic_tilt_shift, TILT_SHIFT_RADIAL),
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
