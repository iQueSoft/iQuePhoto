package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IStartActivityView;

import javax.inject.Inject;

public class StartActivityPresenterImpl implements IStartActivityPresenter {
    private IStartActivityView view;

    @Inject
    public StartActivityPresenterImpl(IStartActivityView view) {
        this.view = view;
    }

    @Override
    public void openCamera() {
        view.startCamera();
    }

    @Override
    public void openGallery() {
        view.startGallery();
    }
}
