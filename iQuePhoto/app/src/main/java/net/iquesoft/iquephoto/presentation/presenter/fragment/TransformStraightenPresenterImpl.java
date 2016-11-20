package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.TransformStraightenView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.EditorCommand.TRANSFORM_STRAIGHTEN;

public class TransformStraightenPresenterImpl implements TransformStraightenPresenter {

    private TransformStraightenView mView;
    private EditorView mEditorView;

    @Inject
    public TransformStraightenPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void setupTransform(EditorCommand editorCommand) {
        mEditorView.setupToolFragment(editorCommand);
    }

    @Override
    public void init(TransformStraightenView view) {
        mView = view;
        mEditorView.setEditorCommand(TRANSFORM_STRAIGHTEN);
    }
}
