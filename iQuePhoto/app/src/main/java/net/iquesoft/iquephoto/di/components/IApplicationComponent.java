package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.PhotoApplication;
import net.iquesoft.iquephoto.di.modules.ApplicationModule;
import net.iquesoft.iquephoto.ui.fragment.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragment.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFragment;
import net.iquesoft.iquephoto.ui.fragment.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragment.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.FramesFragment;
import net.iquesoft.iquephoto.ui.fragment.OverlayFragment;
import net.iquesoft.iquephoto.ui.fragment.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragment.StickersFragment;
import net.iquesoft.iquephoto.ui.fragment.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragment.ToolsFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformHorizontalFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformStraightenFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformVerticalFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class}
)
public interface IApplicationComponent {
    void inject(PhotoApplication application);

    ToolsFragment toolsFragment();

    FiltersFragment filtersFragment();

    AdjustFragment adjustFragment();

    OverlayFragment overlayFragment();

    StickersFragment stickersFragment();

    FramesFragment framesFragment();

    TransformFragment transformFragment();

    TransformVerticalFragment transformVerticalFragment();

    TransformStraightenFragment transformStraightenFragment();

    TransformHorizontalFragment transformHorizontalFragment();

    TiltShiftFragment tiltShiftFragment();

    DrawingFragment drawingFragment();

    AddTextFragment addTextFragment();

    SliderControlFragment sliderControlFragment();

    CameraFragment cameraFragment();

    Camera2Fragment camera2Fragment();

    CameraFiltersFragment cameraFiltersFragment();
}
