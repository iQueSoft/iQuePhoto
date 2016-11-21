package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.TransformVerticalPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.TransformVerticalView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.TRANSFORM_VERTICAL;

public class TransformVerticalPresenterImpl implements TransformVerticalPresenter {

    private TransformVerticalView mView;
    private EditorView mEditorView;

    @Inject
    public TransformVerticalPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(TransformVerticalView view) {
        mView = view;
        mEditorView.setEditorCommand(TRANSFORM_VERTICAL);
    }

    @Override
    public void setupTransform(EditorCommand editorCommand) {
        mEditorView.setupToolFragment(TRANSFORM_VERTICAL);
    }
}
