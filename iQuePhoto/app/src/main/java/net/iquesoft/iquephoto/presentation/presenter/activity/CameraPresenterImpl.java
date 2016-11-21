package net.iquesoft.iquephoto.presentation.presenter.activity;

import android.content.Context;

import net.iquesoft.iquephoto.presentation.presenter.activity.interfaces.CameraPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.CameraView;
import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;

import javax.inject.Inject;

public class CameraPresenterImpl implements CameraPresenter {

    private CameraView mView;

    @Inject
    public CameraPresenterImpl(CameraView view) {
        mView = view;
    }

    @Override
    public void initializeCamera(Context context, Camera2Fragment camera2Fragment) {
        mView.setupCamera(camera2Fragment);
    }
}
