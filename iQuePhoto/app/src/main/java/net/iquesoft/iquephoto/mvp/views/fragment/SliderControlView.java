package net.iquesoft.iquephoto.mvp.views.fragment;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

public interface SliderControlView extends MvpView {
    void initializeCancelButton(@StringRes int toolTitle);

    void initializeSlider(int minValue, int maxValue, int value);
}
