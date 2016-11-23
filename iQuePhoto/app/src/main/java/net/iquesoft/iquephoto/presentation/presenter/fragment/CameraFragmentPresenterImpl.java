package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.CameraFragmentPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.CameraActivityView;
import net.iquesoft.iquephoto.presentation.view.fragment.CameraFragmentView;

import javax.inject.Inject;

public class CameraFragmentPresenterImpl implements CameraFragmentPresenter {
    private CameraFragmentView mView;
    private CameraActivityView mCameraActivityView;

    @Inject
    public CameraFragmentPresenterImpl(CameraActivityView cameraActivityView) {
        mCameraActivityView = cameraActivityView;
    }

    @Override
    public void init(CameraFragmentView view) {
        mView = view;
    }
}
