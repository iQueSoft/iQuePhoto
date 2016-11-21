package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.TransformHorizontalPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.TransformHorizontalView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.TRANSFORM_HORIZONTAL;

public class TransformHorizontalPresenterImpl implements TransformHorizontalPresenter {

    private TransformHorizontalView mView;
    private EditorView mEditorView;

    @Inject
    public TransformHorizontalPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(TransformHorizontalView view) {
        mView = view;
        mEditorView.setEditorCommand(TRANSFORM_HORIZONTAL);
    }
}
