package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.EditorActivityModule;
import net.iquesoft.iquephoto.view.fragment.AdjustFragment;
import net.iquesoft.iquephoto.view.fragment.BrightnessFragment;
import net.iquesoft.iquephoto.view.fragment.DrawingFragment;
import net.iquesoft.iquephoto.view.fragment.FiltersFragment;
import net.iquesoft.iquephoto.view.activity.EditorActivity;
import net.iquesoft.iquephoto.view.fragment.FramesFragment;
import net.iquesoft.iquephoto.view.fragment.OverlayFragment;
import net.iquesoft.iquephoto.view.fragment.ShowStickersFragment;
import net.iquesoft.iquephoto.view.fragment.StickersFragment;
import net.iquesoft.iquephoto.view.fragment.TextFragment;
import net.iquesoft.iquephoto.view.fragment.TiltShiftFragment;
import net.iquesoft.iquephoto.view.fragment.ToolsFragment;

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

    void inject(BrightnessFragment brightnessFragment);
}
