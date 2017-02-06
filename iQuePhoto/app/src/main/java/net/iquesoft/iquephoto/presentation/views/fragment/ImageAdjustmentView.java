package net.iquesoft.iquephoto.presentation.views.fragment;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.enums.EditorTool;

public interface ImageAdjustmentView extends MvpView {
    void changeToolbarTitle(@StringRes int title);

    void changeToolbarSubtitle(@StringRes int subtitle);

    void onIntensityChanged(int value);

    void onBrightnessChanged(int value);

    void onContrastChanged(int value);

    void onSaturationChanged(int value);

    void onWarmthChanged(int value);

    void onStraightenTransformChanged(int value);

    void onVignetteChanged(int value);

    void setEditorTool(EditorTool tool);

    void setSeekBarValues(int minValue, int maxValue, int value);
}
