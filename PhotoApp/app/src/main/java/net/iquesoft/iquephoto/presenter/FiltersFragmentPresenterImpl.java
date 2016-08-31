package net.iquesoft.iquephoto.presenter;

import android.content.Context;

import net.iquesoft.iquephoto.adapters.FiltersAdapter;
import net.iquesoft.iquephoto.model.Filter;
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
