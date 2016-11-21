package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.DrawingPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.DrawingView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.DRAWING;

public class DrawingPresenterImpl implements DrawingPresenter {
    private DrawingView mView;
    private EditorView mEditorView;

    @Inject
    public DrawingPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(DrawingView view) {
        mView = view;
        mEditorView.setEditorCommand(DRAWING);
    }
}
