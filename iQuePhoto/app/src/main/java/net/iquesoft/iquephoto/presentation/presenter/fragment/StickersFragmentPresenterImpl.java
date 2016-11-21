package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.StickersPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.StickersView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.STICKERS;

public class StickersFragmentPresenterImpl implements StickersPresenter {

    private StickersView mView;
    private EditorView mEditorView;

    @Inject
    public StickersFragmentPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(StickersView view) {
        mView = view;
        mEditorView.setEditorCommand(STICKERS);
    }
}
