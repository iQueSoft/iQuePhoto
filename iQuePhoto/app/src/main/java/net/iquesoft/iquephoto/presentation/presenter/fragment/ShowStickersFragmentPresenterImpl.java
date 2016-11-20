package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.ShowStickersView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.EditorCommand.STICKERS;

public class ShowStickersFragmentPresenterImpl implements ShowStickersPresenter {

    private ShowStickersView mView;
    private EditorView mEditorView;

    @Inject
    public ShowStickersFragmentPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(ShowStickersView view) {
        mView = view;
        mEditorView.setEditorCommand(STICKERS);
    }
}
