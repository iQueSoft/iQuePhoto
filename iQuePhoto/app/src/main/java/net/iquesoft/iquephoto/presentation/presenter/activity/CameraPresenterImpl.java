package net.iquesoft.iquephoto.presentation.presenter.activity;

import android.os.Build;

import net.iquesoft.iquephoto.presentation.presenter.activity.interfaces.CameraPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.CameraActivityView;
import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFragment;

import javax.inject.Inject;

public class CameraPresenterImpl implements CameraPresenter {
    private boolean mIsFilterVisible;

    private CameraActivityView mView;

    @Inject
    public CameraPresenterImpl(CameraActivityView view) {
        mView = view;
    }

    @Override
    public void initializeCamera(CameraFragment cameraFragment, Camera2Fragment camera2Fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mView.setupCamera(camera2Fragment);
        else
            mView.setupCamera(cameraFragment);
    }

    @Override
    public void showFilters(CameraFiltersFragment cameraFiltersFragment) {
        if (!mIsFilterVisible) {
            mView.setupFragment(cameraFiltersFragment);
            mIsFilterVisible = true;
        } else {
            mView.hideFiltersButton();
            mIsFilterVisible = false;
        }
    }
}
