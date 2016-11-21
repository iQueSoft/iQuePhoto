package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.fragment.Camera2PresenterImpl;
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

    @Provides
    Camera2PresenterImpl provideCamera2PresenterImpl() {
        return new Camera2PresenterImpl();
    }

}
