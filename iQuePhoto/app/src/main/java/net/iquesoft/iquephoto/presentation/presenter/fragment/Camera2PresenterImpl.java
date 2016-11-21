package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.Camera2View;

import javax.inject.Inject;

public class Camera2PresenterImpl implements Camera2Presenter {
    private Camera2View mView;

    @Inject
    public Camera2PresenterImpl() {
    }

    @Override
    public void init(Camera2View view) {
        mView = view;
    }
}
