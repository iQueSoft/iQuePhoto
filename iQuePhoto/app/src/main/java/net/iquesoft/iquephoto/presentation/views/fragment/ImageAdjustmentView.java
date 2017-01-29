package net.iquesoft.iquephoto.presentation.views.fragment;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.enums.EditorTool;

public interface ImageAdjustmentView extends MvpView {
    void changeToolbarTitle(@StringRes int title);

    void changeToolbarSubtitle(@StringRes int subtitle);

    void onIntensityValueChanged(int value);

    void onBrightnessValueChanged(int value);

    void onContrastValueChanged(int value);

    void onWarmthValueChanged(int value);

    void onStraightenValueChanged(int value);

    void onVignetteValueChanged(int value);

    void setupImageEditorCommand(EditorTool command);

    void initializeSlider(int minValue, int maxValue, int value);
}
