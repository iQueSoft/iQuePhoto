package net.iquesoft.iquephoto.di.modules;

import android.graphics.ColorMatrix;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.model.EditorFrame;
import net.iquesoft.iquephoto.models.Adjust;
import net.iquesoft.iquephoto.models.BrushSize;
import net.iquesoft.iquephoto.models.EditorColor;
import net.iquesoft.iquephoto.models.Filter;
import net.iquesoft.iquephoto.models.Font;
import net.iquesoft.iquephoto.models.Frame;
import net.iquesoft.iquephoto.models.Overlay;
import net.iquesoft.iquephoto.models.Sticker;
import net.iquesoft.iquephoto.models.StickersSet;
import net.iquesoft.iquephoto.models.Tool;
import net.iquesoft.iquephoto.ui.fragments.ImageAdjustmentFragment;
import net.iquesoft.iquephoto.ui.fragments.TextFragment;
import net.iquesoft.iquephoto.ui.fragments.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragments.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragments.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragments.FramesFragment;
import net.iquesoft.iquephoto.ui.fragments.OverlaysFragment;
import net.iquesoft.iquephoto.ui.fragments.StickersSetFragment;
import net.iquesoft.iquephoto.ui.fragments.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragments.TransformFragment;

import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.BRIGHTNESS;
import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.CONTRAST;
import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.SATURATION;
import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.VIGNETTE;

@Module
public class EditorModule {
    @Provides
    List<Tool> provideTools() {
        return Arrays.asList(
                new Tool(R.string.filters, R.drawable.ic_filter, FiltersFragment.newInstance()),
                new Tool(R.string.adjust, R.drawable.ic_adjust, new AdjustFragment()),
                new Tool(R.string.overlay, R.drawable.ic_overlay, new OverlaysFragment()),
                new Tool(R.string.stickers, R.drawable.ic_stiker, new StickersSetFragment()),
                new Tool(R.string.frames, R.drawable.ic_frame, new FramesFragment()),
                new Tool(R.string.transform, R.drawable.ic_transform, new TransformFragment()),
                new Tool(R.string.vignette, R.drawable.ic_vignette, ImageAdjustmentFragment.newInstance(VIGNETTE)),
                new Tool(R.string.tilt_shift, R.drawable.ic_tilt_shift, new TiltShiftFragment()),
                new Tool(R.string.drawing, R.drawable.ic_brush, new DrawingFragment()),
                new Tool(R.string.text, R.drawable.ic_letters, new TextFragment())
        );
    }

    @Provides
    List<Filter> provideFilters() {
        return Arrays.asList(
                new Filter("F02", new ColorMatrix(new float[]{
                        1, 0, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0.50f, 0, 1, 0, 0,
                        0, 0, 0, 1, 0})),

                new Filter("F03", new ColorMatrix(new float[]{
                        1.5f, 0, 0, 0, -40,
                        0, 1.5f, 0, 0, -40,
                        0, 0, 1.5f, 0, -40,
                        0, 0, 0, 1, 0})),

                new Filter("F01", new ColorMatrix(new float[]{
                        2, -1, 0, 0, 0,
                        -1, 2, 0, 0, 0,
                        0, -1, 2, 0, 0,
                        0, 0, 0, 1, 0})),

                new Filter("F12", new ColorMatrix(new float[]{ // TechColor mMatrix
                        1.9125277891456083f, -0.8545344976951645f, -0.09155508482755585f, 0, 11.793603434377337f,
                        -0.3087833385928097f, 1.7658908555458428f, -0.10601743074722245f, 0, -70.35205161461398f,
                        -0.231103377548616f, -0.7501899197440212f, 1.847597816108189f, 0, 30.950940869491138f,
                        0, 0, 0, 1, 0})),

                new Filter("F04", new ColorMatrix(new float[]{
                        1, 0, 0, 0.2f, 0,
                        0, 1, 0, 0, 0,
                        0, 0, 1, 0.2f, 0,
                        0, 0, 0, 1, 0})),

                new Filter("F05", new ColorMatrix(new float[]{
                        1, 0, 0, 0, 0,
                        0, 1.25f, 0, 0, 0,
                        0, 0, 0, 0.5f, 0,
                        0, 0, 0, 1, 0})),

                new Filter("F06", new ColorMatrix(new float[]{
                        1, 0, 0, 0, 0,
                        0, 0.5f, 0, 0, 0,
                        0, 0, 0, 0.5f, 0,
                        0, 0, 0, 1, 0})),

                new Filter("F07", new ColorMatrix(new float[]{
                        1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 1, 1, 0,
                        0, 0, 0, 1, 0})),

                new Filter("F08", new ColorMatrix(new float[]{ // Polaroid mMatrix
                        1.438f, -0.062f, -0.062f, 0, 0,
                        -0.122f, 1.378f, -0.122f, 0, 0,
                        -0.016f, -0.016f, 1.483f, 0, 0,
                        0, 0, 0, 1, 0})),

                new Filter("F09", new ColorMatrix(new float[]{ // CodaChrome mMatrix
                        1.1285582396593525f, -0.3967382283601348f, -0.03992559172921793f, 0, 63.72958762196502f,
                        -0.16404339962244616f, 1.0835251566291304f, -0.05498805115633132f, 0, 24.732407896706203f,
                        -0.16786010706155763f, -0.5603416277695248f, 1.6014850761964943f, 0, 35.62982807460946f,
                        0, 0, 0, 1, 0})),

                new Filter("F10", new ColorMatrix(new float[]{ // LSD mMatrix
                        2, -0.4f, 0.5f, 0, 0,
                        -0.5f, 2, -0.4f, 0, 0,
                        -0.4f, -0.5f, 3, 0, 0,
                        0, 0, 0, 1, 0})),

                new Filter("F11", new ColorMatrix(new float[]{ // Vintage mMatrix
                        0.6279345635605994f, 0.3202183420819367f, -0.03965408211312453f, 0, 9.651285835294123f,
                        0.02578397704808868f, 0.6441188644374771f, 0.03259127616149294f, 0, 7.462829176470591f,
                        0.0466055556782719f, -0.0851232987247891f, 0.5241648018700465f, 0, 5.159190588235296f,
                        0, 0, 0, 1, 0})),

                new Filter("F13", new ColorMatrix(new float[]{ // Browni mMatrix
                        0.5997023498159715f, 0.34553243048391263f, -0.2708298674538042f, 0, 47.43192855600873f,
                        -0.037703249837783157f, 0.8609577587992641f, 0.15059552388459913f, 0, -36.96841498319127f,
                        0.24113635128153335f, -0.07441037908422492f, 0.44972182064877153f, 0, -7.562075277591283f,
                        0, 0, 0, 1, 0})),

                new Filter("BW01", new ColorMatrix(new float[]{
                        1, 0, 0, 0, 0,
                        1, 0, 0, 0, 0,
                        1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0})),

                new Filter("BW02", new ColorMatrix(new float[]{
                        0, 1, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0, 0, 0, 1, 0})),

                new Filter("BW03", new ColorMatrix(new float[]{
                        0, 0, 1, 0, 0,
                        0, 0, 1, 0, 0,
                        0, 0, 1, 0, 0,
                        0, 0, 0, 1, 0}))
        );
    }

    @Provides
    List<Adjust> provideAdjust() {
        return Arrays.asList(
                new Adjust(R.string.brightness, R.drawable.ic_brightness,
                        ImageAdjustmentFragment.newInstance(BRIGHTNESS)),
                new Adjust(R.string.contrast, R.drawable.ic_contrast,
                        ImageAdjustmentFragment.newInstance(CONTRAST)),
                new Adjust(R.string.saturation, R.drawable.ic_saturation,
                        ImageAdjustmentFragment.newInstance(SATURATION))
                //  TODO: new Adjust(R.string.warmth, R.drawable.ic_warmth, ),
                //  TODO: new Adjust(R.string.shadows, R.drawable.ic_shadows, ),
                // TODO: new Adjust(R.string.tint, R.drawable.ic_fade, TINT, ),
                // TODO: new Adjust(R.string.exposure, R.drawable.ic_exposure, EXPOSURE, ),
                // TODO: new Adjust(R.string.fade, R.drawable.ic_fade, )
        );
    }

    @Provides
    List<StickersSet> provideStickersSet() {
        return Arrays.asList(
                new StickersSet(R.string.emoticons, R.drawable.s_emoji_01, provideEmoticonsStickers()),
                new StickersSet(R.string.flags, R.drawable.ic_flags, provideFlagStickers()),
                new StickersSet(R.string.halloween, R.drawable.s_halloween_01, provideHalloweenStickers()),
                new StickersSet(R.string.christmas, R.drawable.s_christmas_01, provideChristmasStickers()),
                new StickersSet(R.string.valentines_day, R.drawable.s_valentines_day_01, provideValentinesDayStickers())
        );
    }

    @Provides
    List<Overlay> provideOverlays() {
        return Arrays.asList(
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

    @Provides
    List<Font> provideFonts() {
        return Arrays.asList(
                new Font("Souses", "Souses.otf"),
                new Font("Black Sword", "Blacksword.otf"),
                new Font("Summer Hearts", "SummerHearts-Regular.otf"),
                new Font("Cigarettes & Coffee", "CigarettesAndCoffee.ttf"),
                new Font("Abys", "Abys-Regular.otf"),
                new Font("Reis", "REIS-Regular.ttf"));
    }

    @Provides
    List<EditorColor> provideColors() {
        return Arrays.asList(
                new EditorColor(R.color.white),
                new EditorColor(R.color.black),
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
                new EditorColor(R.color.indigo));
    }

    @Provides
    List<BrushSize> provideSizes() {
        return Arrays.asList(
                new BrushSize(5f),
                new BrushSize(7.5f),
                new BrushSize(10f),
                new BrushSize(12.5f),
                new BrushSize(15f),
                new BrushSize(17.5f),
                new BrushSize(20f),
                new BrushSize(22.5f),
                new BrushSize(25f),
                new BrushSize(27.5f)
        );
    }

    @Provides
    EditorFrame provideEditorFrame() {
        return new EditorFrame(App.getAppComponent().getContext());
    }

    private List<Sticker> provideEmoticonsStickers() {
        return Arrays.asList(
                new Sticker(R.drawable.s_emoji_01),
                new Sticker(R.drawable.s_emoji_02),
                new Sticker(R.drawable.s_emoji_03),
                new Sticker(R.drawable.s_emoji_04),
                new Sticker(R.drawable.s_emoji_05),
                new Sticker(R.drawable.s_emoji_06),
                new Sticker(R.drawable.s_emoji_07));
    }

    private List<Sticker> provideFlagStickers() {
        return Arrays.asList(
                new Sticker(R.drawable.s_flag_01),
                new Sticker(R.drawable.s_flag_02),
                new Sticker(R.drawable.s_flag_03),
                new Sticker(R.drawable.s_flag_04),
                new Sticker(R.drawable.s_flag_05),
                new Sticker(R.drawable.s_flag_06));
    }

    private List<Sticker> provideChristmasStickers() {
        return Arrays.asList(
                new Sticker(R.drawable.s_flag_01),
                new Sticker(R.drawable.s_flag_02),
                new Sticker(R.drawable.s_flag_03),
                new Sticker(R.drawable.s_flag_04),
                new Sticker(R.drawable.s_flag_05),
                new Sticker(R.drawable.s_flag_06));
    }

    private List<Sticker> provideHalloweenStickers() {
        return Arrays.asList(
                new Sticker(R.drawable.s_halloween_01),
                new Sticker(R.drawable.s_halloween_02),
                new Sticker(R.drawable.s_halloween_03));
    }

    private List<Sticker> provideValentinesDayStickers() {
        return Arrays.asList(
                new Sticker(R.drawable.s_valentines_day_01),
                new Sticker(R.drawable.s_valentines_day_02),
                new Sticker(R.drawable.s_valentines_day_03),
                new Sticker(R.drawable.s_valentines_day_04));
    }
}