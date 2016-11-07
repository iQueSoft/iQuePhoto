package net.iquesoft.iquephoto.presenter.activity;

import net.iquesoft.iquephoto.presenter.activity.interfaces.IStartActivityPresenter;
import net.iquesoft.iquephoto.view.activity.interfaces.IStartActivityView;

import javax.inject.Inject;

public class StartActivityPresenterImpl implements IStartActivityPresenter {
    private IStartActivityView mView;

    @Inject
    public StartActivityPresenterImpl(IStartActivityView view) {
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
