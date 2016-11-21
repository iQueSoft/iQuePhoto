package net.iquesoft.iquephoto.presentation.presenter.activity.interfaces;

import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFragment;

public interface CameraPresenter {
    void initializeCamera(CameraFragment cameraFragment, Camera2Fragment camera2Fragment);

    void showFilters(CameraFiltersFragment cameraFiltersFragment);
}
