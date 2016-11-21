package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.CameraFragmentPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.CameraView;
import net.iquesoft.iquephoto.presentation.view.fragment.CameraFragmentView;

import javax.inject.Inject;

public class CameraFragmentPresenterImpl implements CameraFragmentPresenter {
    private CameraFragmentView mView;
    private CameraView mCameraView;

    @Inject
    public CameraFragmentPresenterImpl(CameraView cameraView) {
        mCameraView = cameraView;
    }

    @Override
    public void init(CameraFragmentView view) {
        mView = view;
    }
}
