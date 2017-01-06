package net.iquesoft.iquephoto.presentation.views.activity;

import android.graphics.ColorMatrix;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpView;

public interface CameraActivityView extends MvpView {
    void setupCamera(Fragment fragment);

    void setupFragment(Fragment fragment);

    void hideFiltersButton();

    void setFilter(ColorMatrix colorMatrix);
}
