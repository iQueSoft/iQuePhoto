package net.iquesoft.iquephoto.mvp.views.fragment;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;

public interface SliderControlView extends MvpView {
    void changeToolbarTitle(@StringRes int title);

    void onStraightenValueChanged(int value);

    void onVignetteValueChanged(int value);

    void setupImageEditorCommand(EditorCommand command);

    void initializeSlider(int minValue, int maxValue, int value);
}
