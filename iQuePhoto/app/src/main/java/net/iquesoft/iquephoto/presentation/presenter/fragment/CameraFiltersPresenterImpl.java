package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.model.Filter;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.CameraFiltersPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.CameraView;
import net.iquesoft.iquephoto.presentation.view.fragment.CameraFiltersView;

import java.util.List;

import javax.inject.Inject;

public class CameraFiltersPresenterImpl implements CameraFiltersPresenter {
    private CameraFiltersView mView;
    private CameraView mCameraView;

    private List<Filter> mFilters;

    @Inject
    public CameraFiltersPresenterImpl(CameraView editorView) {
        mCameraView = editorView;
        mFilters = Filter.getFiltersList();
    }

    @Override
    public void init(CameraFiltersView view) {
        mView = view;
        mView.setupFiltersAdapter(mFilters);
    }
}
