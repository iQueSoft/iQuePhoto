package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.activity.CameraPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.Camera2PresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.CameraFiltersPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.CameraFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.CameraActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraActivityModule {

    private CameraActivityView mView;

    public CameraActivityModule(CameraActivityView view) {
        mView = view;
    }

    @Provides
    CameraActivityView provideCameraActivityView() {
        return mView;
    }

    @Provides
    CameraPresenterImpl provideCameraPresenterImpl() {
        return new CameraPresenterImpl(mView);
    }

    @Provides
    CameraFragmentPresenterImpl provideCameraFragmentPresenterImpl() {
        return new CameraFragmentPresenterImpl(mView);
    }

    @Provides
    Camera2PresenterImpl provideCamera2PresenterImpl() {
        return new Camera2PresenterImpl(mView);
    }

    @Provides
    CameraFiltersPresenterImpl provideCameraFiltersPresenterImpl() {
        return new CameraFiltersPresenterImpl(mView);
    }
}
