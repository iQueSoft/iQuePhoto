package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.EditorActivityModule;
import net.iquesoft.iquephoto.ui.fragment.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragment.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragment.FiltersFragment;
import net.iquesoft.iquephoto.ui.activity.EditorActivity;
import net.iquesoft.iquephoto.ui.fragment.FramesFragment;
import net.iquesoft.iquephoto.ui.fragment.OverlayFragment;
import net.iquesoft.iquephoto.ui.fragment.ShowStickersFragment;
import net.iquesoft.iquephoto.ui.fragment.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragment.StickersFragment;
import net.iquesoft.iquephoto.ui.fragment.TextFragment;
import net.iquesoft.iquephoto.ui.fragment.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragment.ToolsFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = IApplicationComponent.class,
        modules = EditorActivityModule.class)

public interface IEditorActivityComponent {

    void inject(EditorActivity editorActivity);

    void inject(ToolsFragment toolsFragment);

    void inject(FiltersFragment filtersFragment);

    void inject(AdjustFragment adjustFragment);

    void inject(TextFragment textFragment);

    void inject(DrawingFragment drawingFragment);

    void inject(StickersFragment stickersFragment);

    void inject(ShowStickersFragment showStickersFragment);

    void inject(OverlayFragment overlayFragment);

    void inject(FramesFragment framesFragment);

    void inject(TiltShiftFragment tiltShiftFragment);

    void inject(TransformFragment transformFragment);

    void inject(SliderControlFragment sliderControlFragment);
}
