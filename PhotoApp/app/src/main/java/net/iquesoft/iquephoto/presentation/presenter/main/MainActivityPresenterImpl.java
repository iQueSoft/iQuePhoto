package net.iquesoft.iquephoto.presentation.presenter.main;

import net.iquesoft.iquephoto.presentation.view.main.MainView;

import javax.inject.Inject;

public class MainActivityPresenterImpl implements IMainActivityPresenter {
    private MainView mView;

    @Inject
    public MainActivityPresenterImpl(MainView view) {
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
