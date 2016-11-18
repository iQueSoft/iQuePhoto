package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.AdjustView;

import javax.inject.Inject;

public class AdjustPresenterImpl implements AdjustPresenter {

    private AdjustView mView;

    @Inject
    public AdjustPresenterImpl() {

    }

    @Override
    public void init(AdjustView view) {
        mView = view;
    }
}
