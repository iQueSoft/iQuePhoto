package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.enums.EditorTool;

public interface TiltShiftView extends MvpView {
    void onTiltShiftChanged(EditorTool command);
}