package net.iquesoft.iquephoto.di.modules;

import android.content.Context;

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

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
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
    OverlaysFragment provideOverlayFragment() {
        return new OverlaysFragment();
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
