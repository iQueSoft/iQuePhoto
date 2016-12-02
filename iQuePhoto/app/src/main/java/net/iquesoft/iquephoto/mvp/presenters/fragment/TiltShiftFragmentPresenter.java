package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.mvp.views.fragment.TiltShiftView;

@InjectViewState
public class TiltShiftFragmentPresenter extends MvpPresenter<TiltShiftView> {
    private EditorCommand mCurrentTiltShift;

    public void changeTiltShift(EditorCommand command) {
        mCurrentTiltShift = command;
        getViewState().onTiltShiftChanged(mCurrentTiltShift);
    }

    public void applyChanges() {
        getViewState().applyTiltShift(mCurrentTiltShift);
    }
}
