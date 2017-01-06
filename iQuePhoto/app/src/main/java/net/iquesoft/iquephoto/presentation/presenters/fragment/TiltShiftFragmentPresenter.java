package net.iquesoft.iquephoto.presentation.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.presentation.views.fragment.TiltShiftView;

@InjectViewState
public class TiltShiftFragmentPresenter extends MvpPresenter<TiltShiftView> {
    private EditorTool mCurrentTiltShift;

    public void changeTiltShift(EditorTool command) {
        mCurrentTiltShift = command;
        getViewState().onTiltShiftChanged(mCurrentTiltShift);
    }

    public void applyChanges() {
        getViewState().applyTiltShift(mCurrentTiltShift);
    }
}
