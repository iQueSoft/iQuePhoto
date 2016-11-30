package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.models.Filter;
import net.iquesoft.iquephoto.mvp.views.activity.CameraActivityView;
import net.iquesoft.iquephoto.mvp.views.fragment.CameraFiltersView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class CameraFiltersPresenter extends MvpPresenter<CameraFiltersView> {
    private List<Filter> mFilters = Filter.getFiltersList();

    public void onFilterClick(Filter filter) {
        // TODO: mCameraActivityView.setFilter(filter.getColorMatrix());
    }
}
