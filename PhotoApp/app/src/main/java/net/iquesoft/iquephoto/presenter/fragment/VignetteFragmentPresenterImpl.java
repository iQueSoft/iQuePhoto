package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.IVignetteFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IVignetteFragmentView;

import javax.inject.Inject;

public class VignetteFragmentPresenterImpl implements IVignetteFragmentPresenter {

    private IVignetteFragmentView mView;

    @Inject
    public VignetteFragmentPresenterImpl() {

    }

    @Override
    public void init(IVignetteFragmentView view) {
        mView = view;
    }
}
