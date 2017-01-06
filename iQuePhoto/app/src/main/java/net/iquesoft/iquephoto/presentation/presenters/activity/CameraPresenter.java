package net.iquesoft.iquephoto.presentation.presenters.activity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.presentation.views.activity.CameraActivityView;
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
