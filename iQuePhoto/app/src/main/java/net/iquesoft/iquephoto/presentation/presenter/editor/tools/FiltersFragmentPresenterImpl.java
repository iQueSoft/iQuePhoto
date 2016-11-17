package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.presentation.view.editor.tools.FiltersView;

import javax.inject.Inject;

public class FiltersFragmentPresenterImpl implements IFiltersFragmentPresenter {

    private FiltersView mView;

    @Inject
    public FiltersFragmentPresenterImpl() {

    }

    @Override
    public void init(FiltersView view) {
        mView = view;
    }

    @Override
    public void onResume() {
        mView.setFiltersAdapter();
    }
}
