package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;

public interface SliderControlView extends MvpView {
    void setupImageEditorCommand(EditorCommand command);

    void initializeSlider(int minValue, int maxValue, int value);
}
