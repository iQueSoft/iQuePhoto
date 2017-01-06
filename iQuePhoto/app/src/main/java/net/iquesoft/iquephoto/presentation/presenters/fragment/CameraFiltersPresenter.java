package net.iquesoft.iquephoto.presentation.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.models.Filter;
import net.iquesoft.iquephoto.presentation.views.fragment.CameraFiltersView;

@InjectViewState
public class CameraFiltersPresenter extends MvpPresenter<CameraFiltersView> {
    /*@Inject
    List<Filter> mFilters;*/

    public void onFilterClick(Filter filter) {
        // TODO: mCameraActivityView.setFilter(filter.getColorMatrix());
    }
}
