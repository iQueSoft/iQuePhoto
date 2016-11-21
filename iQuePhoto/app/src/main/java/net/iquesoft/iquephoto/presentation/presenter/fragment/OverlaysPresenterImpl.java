package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.OverlaysPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.OverlayView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.OVERLAY;

public class OverlaysPresenterImpl implements OverlaysPresenter {

    private OverlayView mView;
    private EditorView mEditorView;

    @Inject
    public OverlaysPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(OverlayView view) {
        mView = view;
        mEditorView.setEditorCommand(OVERLAY);
    }
}
