package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IOverlayFragmentView;

import javax.inject.Inject;

public class OverlayFragmentPresenterImpl implements IOverlayFragmentPresenter {

    private IOverlayFragmentView mView;

    @Inject
    public OverlayFragmentPresenterImpl() {

    }

    @Override
    public void init(IOverlayFragmentView view) {
        mView = view;
    }
}
