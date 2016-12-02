package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.models.Filter;
import net.iquesoft.iquephoto.mvp.views.fragment.FiltersView;

import java.util.List;

@InjectViewState
public class FiltersPresenter extends MvpPresenter<FiltersView> {
    private List<Filter> mFilters = Filter.getFiltersList();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setupFiltersAdapter(mFilters);
    }
}
