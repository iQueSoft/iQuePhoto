package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.view.activity.CameraView;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraActivityModule {

    private CameraView mView;

    public CameraActivityModule(CameraView view) {
        mView = view;
    }

    @Provides
    CameraView provideCameraActivityView() {
        return mView;
    }
}
