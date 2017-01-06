package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Filter;

import java.util.List;

public interface FiltersView extends MvpView {
    void setupFiltersAdapter(List<Filter> filters);
}
