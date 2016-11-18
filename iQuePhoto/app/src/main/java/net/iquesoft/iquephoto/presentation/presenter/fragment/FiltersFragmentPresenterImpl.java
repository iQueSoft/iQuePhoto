package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.FiltersView;

import javax.inject.Inject;

public class FiltersFragmentPresenterImpl implements FiltersPresenter {

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
