package net.iquesoft.iquephoto.presentation.view.activity;

import android.support.v4.app.Fragment;

public interface CameraActivityView {
    void setupCamera(Fragment fragment);

    void setupFragment(Fragment fragment);

    void hideFiltersButton();
}
