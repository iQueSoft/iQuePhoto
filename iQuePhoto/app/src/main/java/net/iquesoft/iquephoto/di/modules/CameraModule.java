package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.views.activity.CameraActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraModule {
    private CameraActivityView mView;

    public CameraModule(CameraActivityView view) {
        mView = view;
    }

    @Provides
    CameraActivityView provideCameraActivityView() {
        return mView;
    }
}
