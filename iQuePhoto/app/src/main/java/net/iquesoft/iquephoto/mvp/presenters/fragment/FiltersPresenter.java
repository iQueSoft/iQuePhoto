package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.mvp.models.Filter;
import net.iquesoft.iquephoto.mvp.views.fragment.FiltersView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class FiltersPresenter extends MvpPresenter<FiltersView> {
    @Inject
    List<Filter> mFilters;

    public FiltersPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupFiltersAdapter(mFilters);
    }
}
