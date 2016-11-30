package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.EditorModule;
import net.iquesoft.iquephoto.ui.fragments.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragments.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragments.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragments.FiltersFragment;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;
import net.iquesoft.iquephoto.ui.fragments.FramesFragment;
import net.iquesoft.iquephoto.ui.fragments.OverlaysFragment;
import net.iquesoft.iquephoto.ui.fragments.StickersFragment;
import net.iquesoft.iquephoto.ui.fragments.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragments.StickersToolFragment;
import net.iquesoft.iquephoto.ui.fragments.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragments.ToolsFragment;
import net.iquesoft.iquephoto.ui.fragments.TransformFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = EditorModule.class)

public interface EditorComponent {

    void inject(EditorActivity editorActivity);

    void inject(ToolsFragment toolsFragment);

    void inject(FiltersFragment filtersFragment);

    void inject(AdjustFragment adjustFragment);

    void inject(AddTextFragment textFragment);

    void inject(DrawingFragment drawingFragment);

    void inject(StickersToolFragment stickersToolFragment);

    void inject(StickersFragment stickersFragment);

    void inject(OverlaysFragment overlayFragment);

    void inject(FramesFragment framesFragment);

    void inject(TiltShiftFragment tiltShiftFragment);

    void inject(TransformFragment transformFragment);

    void inject(SliderControlFragment sliderControlFragment);
}
