package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.AdjustPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.AdjustView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.NONE;

public class AdjustPresenterImpl implements AdjustPresenter {

    private AdjustView mView;
    private EditorView mEditorView;

    @Inject
    public AdjustPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(AdjustView view) {
        mView = view;
        mEditorView.setEditorCommand(NONE);
    }

    @Override
    public void setupAdjust(EditorCommand editorCommand) {
        mEditorView.setupToolFragment(editorCommand);
    }
}