package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.enums.EditorTool;

public interface TiltShiftView extends MvpView {

    void onTiltShiftChanged(EditorTool command);

    void applyTiltShift(EditorTool command);
}
