package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.di.modules.AppModule;
import net.iquesoft.iquephoto.ui.fragment.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragment.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFragment;
import net.iquesoft.iquephoto.ui.fragment.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragment.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.FramesFragment;
import net.iquesoft.iquephoto.ui.fragment.GalleryAlbumsFragment;
import net.iquesoft.iquephoto.ui.fragment.GalleryImagesFragment;
import net.iquesoft.iquephoto.ui.fragment.OverlayFragment;
import net.iquesoft.iquephoto.ui.fragment.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragment.StickersToolFragment;
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
        AppModule.class}
)
public interface AppComponent {
    void inject(App application);

    GalleryAlbumsFragment galleryAlbumsFragment();

    GalleryImagesFragment galleryImagesFragment();

    ToolsFragment toolsFragment();

    FiltersFragment filtersFragment();

    AdjustFragment adjustFragment();

    OverlayFragment overlayFragment();

    StickersToolFragment stickersFragment();

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
