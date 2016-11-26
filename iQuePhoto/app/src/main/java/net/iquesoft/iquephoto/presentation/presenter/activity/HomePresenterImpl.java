package net.iquesoft.iquephoto.presentation.presenter.activity;

import net.iquesoft.iquephoto.presentation.presenter.activity.interfaces.MainPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.HomeView;

import javax.inject.Inject;

public class HomePresenterImpl implements MainPresenter {
    private HomeView mView;

    @Inject
    public HomePresenterImpl(HomeView view) {
        mView = view;
    }

    @Override
    public void openCamera() {
        mView.startCamera();
    }

    @Override
    public void openGallery() {
        mView.startGallery();
    }
}
