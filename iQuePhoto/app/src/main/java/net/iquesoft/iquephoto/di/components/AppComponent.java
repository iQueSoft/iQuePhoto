package net.iquesoft.iquephoto.di.components;

import android.content.Context;

import net.iquesoft.iquephoto.di.modules.AppModule;
import net.iquesoft.iquephoto.ui.fragments.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragments.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragments.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragments.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragments.CameraFragment;
import net.iquesoft.iquephoto.ui.fragments.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragments.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragments.FramesFragment;
import net.iquesoft.iquephoto.ui.fragments.GalleryAlbumsFragment;
import net.iquesoft.iquephoto.ui.fragments.GalleryImagesFragment;
import net.iquesoft.iquephoto.ui.fragments.OverlaysFragment;
import net.iquesoft.iquephoto.ui.fragments.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragments.StickersToolFragment;
import net.iquesoft.iquephoto.ui.fragments.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragments.ToolsFragment;
import net.iquesoft.iquephoto.ui.fragments.TransformFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class}
)
public interface AppComponent {
    Context getContext();

    GalleryAlbumsFragment galleryAlbumsFragment();

    GalleryImagesFragment galleryImagesFragment();

    ToolsFragment toolsFragment();

    FiltersFragment filtersFragment();

    AdjustFragment adjustFragment();

    OverlaysFragment overlayFragment();

    StickersToolFragment stickersFragment();

    FramesFragment framesFragment();

    TransformFragment transformFragment();

    TiltShiftFragment tiltShiftFragment();

    DrawingFragment drawingFragment();

    AddTextFragment addTextFragment();

    SliderControlFragment sliderControlFragment();

    CameraFragment cameraFragment();

    Camera2Fragment camera2Fragment();

    CameraFiltersFragment cameraFiltersFragment();
}
