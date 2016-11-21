package net.iquesoft.iquephoto.presentation.presenter.activity.interfaces;

import android.content.Context;

import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFiltersFragment;

public interface CameraPresenter {
    void initializeCamera(Context context, Camera2Fragment camera2Fragment);

    void showFilters(CameraFiltersFragment cameraFiltersFragment);
}
