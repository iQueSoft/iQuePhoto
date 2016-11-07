package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.IOverlayFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IOverlayFragmentView;

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
