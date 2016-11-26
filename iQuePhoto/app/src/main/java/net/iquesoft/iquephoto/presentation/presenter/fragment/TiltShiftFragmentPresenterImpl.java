package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.TiltShiftPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.TiltShiftView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.TILT_SHIFT_RADIAL;

public class TiltShiftFragmentPresenterImpl implements TiltShiftPresenter {
    private TiltShiftView mView;
    private EditorView mEditorView;

    private EditorCommand mCurrentCommand;

    @Inject
    public TiltShiftFragmentPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(TiltShiftView view) {
        mView = view;
        mEditorView.setEditorCommand(TILT_SHIFT_RADIAL);
    }

    @Override
    public void apply() {
        mEditorView.applyCommand(mCurrentCommand);
    }

    @Override
    public void setCommand(EditorCommand editorCommand) {
        mCurrentCommand = editorCommand;
        mEditorView.setEditorCommand(mCurrentCommand);
    }
}
