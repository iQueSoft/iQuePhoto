package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.view.ICameraActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraActivityModule {

    private final ICameraActivityView view;

    public CameraActivityModule(ICameraActivityView view) {
        this.view = view;
    }

    @Provides
    ICameraActivityView provideCameraActivityView() {
        return view;
    }
}
