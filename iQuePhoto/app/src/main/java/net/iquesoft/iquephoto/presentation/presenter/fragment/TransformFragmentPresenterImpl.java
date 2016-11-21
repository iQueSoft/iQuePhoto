package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.TransformPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.TransformView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.TRANSFORM;

public class TransformFragmentPresenterImpl implements TransformPresenter {

    private TransformView mView;
    private EditorView mEditorView;

    @Inject
    public TransformFragmentPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(TransformView view) {
        mView = view;
        mEditorView.setEditorCommand(TRANSFORM);
    }

    @Override
    public void setupTransform(EditorCommand editorCommand) {
        mEditorView.setupToolFragment(editorCommand);
    }
}
