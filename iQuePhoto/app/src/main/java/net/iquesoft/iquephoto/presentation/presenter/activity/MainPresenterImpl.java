package net.iquesoft.iquephoto.presentation.presenter.activity;

import net.iquesoft.iquephoto.presentation.presenter.activity.interfaces.MainPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.MainView;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter {
    private MainView mView;

    @Inject
    public MainPresenterImpl(MainView view) {
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
