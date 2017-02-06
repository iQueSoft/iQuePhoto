package net.iquesoft.iquephoto.presentation.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.core.enums.EditorTool;
import net.iquesoft.iquephoto.presentation.views.fragment.TiltShiftView;

@InjectViewState
public class TiltShiftFragmentPresenter extends MvpPresenter<TiltShiftView> {
    public void changeTiltShift(EditorTool command) {
        getViewState().onTiltShiftChanged(command);
    }
}