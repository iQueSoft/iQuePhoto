package net.iquesoft.iquephoto.di.modules;

import android.app.Application;

import net.iquesoft.iquephoto.App;
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

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App mApplication;

    public AppModule(App application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    GalleryAlbumsFragment provideGalleryAlbumsFragment() {
        return new GalleryAlbumsFragment();
    }

    @Provides
    @Singleton
    GalleryImagesFragment provideGalleryImagesFragment() {
        return new GalleryImagesFragment();
    }

    @Provides
    @Singleton
    ToolsFragment provideToolsFragment() {
        return new ToolsFragment();
    }

    @Provides
    @Singleton
    FiltersFragment provideFiltersFragment() {
        return new FiltersFragment();
    }

    @Provides
    @Singleton
    AdjustFragment provideAdjustFragment() {
        return new AdjustFragment();
    }

    @Provides
    @Singleton
    OverlayFragment provideOverlayFragment() {
        return new OverlayFragment();
    }

    @Provides
    @Singleton
    StickersToolFragment provideStickersFragment() {
        return new StickersToolFragment();
    }

    @Provides
    @Singleton
    FramesFragment provideFramesFragment() {
        return new FramesFragment();
    }

    @Provides
    @Singleton
    TransformFragment provideTransformFragment() {
        return new TransformFragment();
    }

    @Provides
    @Singleton
    TransformHorizontalFragment provideTransformHorizontalFragment() {
        return new TransformHorizontalFragment();
    }

    @Provides
    @Singleton
    TransformStraightenFragment provideTransformStraightenFragment() {
        return new TransformStraightenFragment();
    }

    @Provides
    @Singleton
    TransformVerticalFragment provideTransformVerticalFragment() {
        return new TransformVerticalFragment();
    }

    @Provides
    @Singleton
    TiltShiftFragment provideTiltShiftFragment() {
        return new TiltShiftFragment();
    }

    @Provides
    @Singleton
    DrawingFragment provideDrawingFragment() {
        return new DrawingFragment();
    }

    @Provides
    @Singleton
    AddTextFragment provideAddTextFragment() {
        return new AddTextFragment();
    }

    @Provides
    @Singleton
    SliderControlFragment provideSliderControlFragment() {
        return new SliderControlFragment();
    }

    @Provides
    @Singleton
    CameraFragment provideCameraFragment() {
        return new CameraFragment();
    }

    @Provides
    @Singleton
    Camera2Fragment provideCamera2Fragment() {
        return new Camera2Fragment();
    }

    @Provides
    @Singleton
    CameraFiltersFragment provideCameraFiltersFragment() {
        return new CameraFiltersFragment();
    }
}
