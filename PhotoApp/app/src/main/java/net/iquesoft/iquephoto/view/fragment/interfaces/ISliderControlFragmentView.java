package net.iquesoft.iquephoto.view.fragment.interfaces;

import android.support.annotation.StringRes;

public interface ISliderControlFragmentView {
    void initializeCancelButton(@StringRes int toolTitle);

    void initializeSlider(int minValue, int maxValue, int value);
}
