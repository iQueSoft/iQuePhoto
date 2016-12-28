package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.mvp.models.Frame;
import net.iquesoft.iquephoto.mvp.models.Overlay;
import net.iquesoft.iquephoto.mvp.models.Tool;
import net.iquesoft.iquephoto.ui.fragments.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragments.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragments.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragments.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragments.FramesFragment;
import net.iquesoft.iquephoto.ui.fragments.OverlaysFragment;
import net.iquesoft.iquephoto.ui.fragments.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragments.StickersToolFragment;
import net.iquesoft.iquephoto.ui.fragments.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragments.TransformFragment;

import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.VIGNETTE;

@Module
public class EditorModule {
    @Provides
    List<Tool> provideTools() {
        return Arrays.asList(
                new Tool(R.string.filters, R.drawable.ic_filter, FiltersFragment.newInstance()),
                new Tool(R.string.adjust, R.drawable.ic_adjust, new AdjustFragment()),
                new Tool(R.string.overlay, R.drawable.ic_overlay, new OverlaysFragment()),
                new Tool(R.string.stickers, R.drawable.ic_stiker, new StickersToolFragment()),
                new Tool(R.string.frames, R.drawable.ic_frame, new FramesFragment()),
                new Tool(R.string.transform, R.drawable.ic_frame, new TransformFragment()),
                new Tool(R.string.vignette, R.drawable.ic_vignette, SliderControlFragment.newInstance(VIGNETTE)),
                new Tool(R.string.tilt_shift, R.drawable.ic_tilt_shift, new TiltShiftFragment()),
                new Tool(R.string.drawing, R.drawable.ic_brush, new DrawingFragment()),
                new Tool(R.string.text, R.drawable.ic_letters, new AddTextFragment())
        );
    }

    @Provides
    List<Overlay> provideOverlays() {
        return Arrays.asList(
                new Overlay("C01", R.drawable.overlay_color_1),
                new Overlay("C02", R.drawable.overlay_color_03),
                new Overlay("C03", R.drawable.overlay_color_04),
                new Overlay("C04", R.drawable.overlay_color_06),
                new Overlay("D01", R.drawable.overlay_dust_02),
                new Overlay("D02", R.drawable.overlay_dust_03),
                new Overlay("FD01", R.drawable.overlay_fd_01),
                new Overlay("FD02", R.drawable.overlay_fd_02)
        );
    }

    @Provides
    List<Frame> provideFrames() {
        return Arrays.asList(
                new Frame("GRUNGE01", R.drawable.frame_grunge_01),
                new Frame("GRUNGE02", R.drawable.frame_grunge_02),
                new Frame("GRUNGE03", R.drawable.frame_grunge_03),
                new Frame("GRUNGE04", R.drawable.frame_grunge_04),
                new Frame("HL01", R.drawable.frame_h_01)
        );
    }
}
