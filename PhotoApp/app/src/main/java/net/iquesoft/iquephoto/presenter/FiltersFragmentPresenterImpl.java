package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IFiltersFragmentView;

import javax.inject.Inject;

public class FiltersFragmentPresenterImpl implements IFiltersFragmentPresenter {

    private IFiltersFragmentView view;

    @Inject
    public FiltersFragmentPresenterImpl() {

    }

    @Override
    public void init(IFiltersFragmentView view) {
        this.view = view;
    }

    @Override
    public void onResume() {
        view.setFiltersAdapter();
    }
}
