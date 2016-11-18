package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.StickersView;

import javax.inject.Inject;

public class StickersFragmentPresenterImpl implements StickersPresenter {

    private StickersView mView;

    @Inject
    public StickersFragmentPresenterImpl() {

    }

    @Override
    public void init(StickersView view) {
        mView = view;
    }
}
