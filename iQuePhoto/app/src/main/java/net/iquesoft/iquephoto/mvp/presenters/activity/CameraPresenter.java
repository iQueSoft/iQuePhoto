package net.iquesoft.iquephoto.mvp.presenters.activity;

import android.os.Build;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.views.activity.CameraActivityView;
import net.iquesoft.iquephoto.ui.fragments.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragments.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragments.CameraFragment;

@InjectViewState
public class CameraPresenter extends MvpPresenter<CameraActivityView> {
    private boolean mIsFilterVisible;

    public void initializeCamera(CameraFragment cameraFragment, Camera2Fragment camera2Fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getViewState().setupCamera(camera2Fragment);
        else
            getViewState().setupCamera(cameraFragment);
    }

    public void showFilters(CameraFiltersFragment cameraFiltersFragment) {
        if (!mIsFilterVisible) {
            getViewState().setupFragment(cameraFiltersFragment);
            mIsFilterVisible = true;
        } else {
            getViewState().hideFiltersButton();
            mIsFilterVisible = false;
        }
    }
}
