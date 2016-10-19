package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IGalleryActivityView;

import javax.inject.Inject;

public class GalleryActivityPresenterImpl implements IGalleryActivityPresenter {

    private IGalleryActivityView mView;

    @Inject
    public GalleryActivityPresenterImpl(IGalleryActivityView view) {
        mView = view;
    }
}
