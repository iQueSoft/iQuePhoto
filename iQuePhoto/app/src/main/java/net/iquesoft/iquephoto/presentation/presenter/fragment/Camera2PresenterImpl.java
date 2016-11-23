package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.Camera2Presenter;
import net.iquesoft.iquephoto.presentation.view.activity.CameraActivityView;
import net.iquesoft.iquephoto.presentation.view.fragment.Camera2FragmentView;

import javax.inject.Inject;

public class Camera2PresenterImpl implements Camera2Presenter {

    private Camera2FragmentView mView;
    private CameraActivityView mCameraActivityView;

    @Inject
    public Camera2PresenterImpl(CameraActivityView cameraActivityView) {
        mCameraActivityView = cameraActivityView;
    }

    @Override
    public void init(Camera2FragmentView view) {
        mView = view;
    }

    @Override
    public void takePhoto() {
        if (mView != null)
            mView.takePhoto();
    }
}
