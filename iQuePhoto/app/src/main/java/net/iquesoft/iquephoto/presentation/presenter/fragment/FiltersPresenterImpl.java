package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.FiltersPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.FiltersView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.FILTERS;

public class FiltersPresenterImpl implements FiltersPresenter {

    private FiltersView mView;
    private EditorView mEditorView;

    @Inject
    public FiltersPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(FiltersView view) {
        mView = view;
        mEditorView.setEditorCommand(FILTERS);
    }

    @Override
    public void onResume() {
        mView.setFiltersAdapter();
    }
}
