package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Filter;

import java.util.List;

public interface FiltersView extends MvpView {
    void setupFiltersAdapter(List<Filter> filters);
}
