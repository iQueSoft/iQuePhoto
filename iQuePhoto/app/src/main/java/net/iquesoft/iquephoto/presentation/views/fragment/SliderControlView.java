package net.iquesoft.iquephoto.presentation.views.fragment;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.enums.EditorTool;

public interface SliderControlView extends MvpView {
    void changeToolbarTitle(@StringRes int title);

    void onStraightenValueChanged(int value);

    void onVignetteValueChanged(int value);

    void setupImageEditorCommand(EditorTool command);

    void initializeSlider(int minValue, int maxValue, int value);
}
