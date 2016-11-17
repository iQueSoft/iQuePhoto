package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.view.camera.ICameraActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraActivityModule {

    private ICameraActivityView mView;

    public CameraActivityModule(ICameraActivityView view) {
        mView = view;
    }

    @Provides
    ICameraActivityView provideCameraActivityView() {
        return mView;
    }
}
