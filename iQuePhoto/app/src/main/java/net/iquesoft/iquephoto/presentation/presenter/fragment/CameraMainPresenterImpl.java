package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.CameraMainPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.CameraView;
import net.iquesoft.iquephoto.presentation.view.fragment.CameraMainView;

import javax.inject.Inject;

public class CameraMainPresenterImpl implements CameraMainPresenter {
    private CameraMainView mView;
    private CameraView mCameraView;

    @Inject
    public CameraMainPresenterImpl(CameraView cameraView) {
        mCameraView = cameraView;
    }

    @Override
    public void init(CameraMainView view) {
        mView = view;
    }
}
