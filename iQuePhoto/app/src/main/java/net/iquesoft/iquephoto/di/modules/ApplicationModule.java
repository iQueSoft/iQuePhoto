package net.iquesoft.iquephoto.di.modules;

import android.app.Application;

import net.iquesoft.iquephoto.PhotoApplication;
import net.iquesoft.iquephoto.ui.fragment.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragment.AdjustFragment;
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

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private PhotoApplication mApplication;

    public ApplicationModule(PhotoApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
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
    StickersFragment provideStickersFragment() {
        return new StickersFragment();
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
}
