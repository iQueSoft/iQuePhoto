package net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.model.Filter;
import net.iquesoft.iquephoto.presentation.view.fragment.CameraFiltersView;

public interface CameraFiltersPresenter extends BaseFragmentPresenter<CameraFiltersView> {
    void onFilterClick(Filter filter);
}
