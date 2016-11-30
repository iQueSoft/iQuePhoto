package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.mvp.views.activity.EditorView;
import net.iquesoft.iquephoto.mvp.views.fragment.TiltShiftView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.TILT_SHIFT_RADIAL;

@InjectViewState
public class TiltShiftFragmentPresenter extends MvpPresenter<TiltShiftView> {

    /* TODO:

    @Override
    public void apply() {
        mEditorView.applyCommand(mCurrentCommand);
    }

    @Override
    public void setCommand(EditorCommand editorCommand) {
        mCurrentCommand = editorCommand;
        mEditorView.setEditorCommand(mCurrentCommand);
    }*/
}
