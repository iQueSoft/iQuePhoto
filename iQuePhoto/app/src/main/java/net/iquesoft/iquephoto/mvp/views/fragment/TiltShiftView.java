package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;

public interface TiltShiftView extends MvpView {

    void onTiltShiftChanged(EditorCommand command);

    void applyTiltShift(EditorCommand command);
}
