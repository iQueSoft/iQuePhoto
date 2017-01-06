package net.iquesoft.iquephoto.mvp.presenters.activity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.views.activity.CameraActivityView;
import net.iquesoft.iquephoto.ui.fragments.CameraFiltersFragment;

@InjectViewState
public class CameraPresenter extends MvpPresenter<CameraActivityView> {
    private boolean mIsFilterVisible;

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
