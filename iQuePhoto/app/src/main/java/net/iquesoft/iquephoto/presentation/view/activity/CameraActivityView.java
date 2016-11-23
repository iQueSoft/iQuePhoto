package net.iquesoft.iquephoto.presentation.view.activity;

import android.graphics.ColorMatrix;
import android.support.v4.app.Fragment;

public interface CameraActivityView {
    void setupCamera(Fragment fragment);

    void setupFragment(Fragment fragment);

    void hideFiltersButton();

    void setFilter(ColorMatrix colorMatrix);
}
