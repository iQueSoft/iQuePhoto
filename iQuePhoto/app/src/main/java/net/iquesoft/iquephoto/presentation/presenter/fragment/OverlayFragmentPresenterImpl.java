package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.OverlayView;

import javax.inject.Inject;

public class OverlayFragmentPresenterImpl implements OverlayPresenter {

    private OverlayView mView;

    @Inject
    public OverlayFragmentPresenterImpl() {

    }

    @Override
    public void init(OverlayView view) {
        mView = view;
    }
}
