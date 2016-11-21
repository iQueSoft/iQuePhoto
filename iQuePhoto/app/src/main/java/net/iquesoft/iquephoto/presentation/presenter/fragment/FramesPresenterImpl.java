package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.FramesView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.EditorCommand.FRAMES;

public class FramesPresenterImpl implements FramesPresenter {

    private FramesView mView;
    private EditorView mEditorView;

    @Inject
    public FramesPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(FramesView view) {
        mView = view;
        mEditorView.setEditorCommand(FRAMES);
    }
}
