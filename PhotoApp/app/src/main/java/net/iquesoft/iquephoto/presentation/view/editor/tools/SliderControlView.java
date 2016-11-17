package net.iquesoft.iquephoto.presentation.view.editor.tools;

import android.support.annotation.StringRes;

public interface SliderControlView {
    void initializeCancelButton(@StringRes int toolTitle);

    void initializeSlider(int minValue, int maxValue, int value);
}
