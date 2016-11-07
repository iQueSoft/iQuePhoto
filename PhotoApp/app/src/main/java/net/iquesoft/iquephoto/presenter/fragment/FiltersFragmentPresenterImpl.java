package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.IFiltersFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IFiltersFragmentView;

import javax.inject.Inject;

public class FiltersFragmentPresenterImpl implements IFiltersFragmentPresenter {

    private IFiltersFragmentView mView;

    @Inject
    public FiltersFragmentPresenterImpl() {

    }

    @Override
    public void init(IFiltersFragmentView view) {
        mView = view;
    }

    @Override
    public void onResume() {
        mView.setFiltersAdapter();
    }
}
