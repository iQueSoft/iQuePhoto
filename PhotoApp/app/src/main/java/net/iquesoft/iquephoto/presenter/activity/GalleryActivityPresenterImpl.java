package net.iquesoft.iquephoto.presenter.activity;

import net.iquesoft.iquephoto.presenter.activity.interfaces.IGalleryActivityPresenter;
import net.iquesoft.iquephoto.view.activity.interfaces.IGalleryActivityView;

import javax.inject.Inject;

public class GalleryActivityPresenterImpl implements IGalleryActivityPresenter {

    private IGalleryActivityView mView;

    @Inject
    public GalleryActivityPresenterImpl(IGalleryActivityView view) {
        mView = view;
    }
}
