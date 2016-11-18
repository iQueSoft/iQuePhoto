package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.ShowStickersView;

import javax.inject.Inject;

public class ShowStickersFragmentPresenterImpl implements ShowStickersPresenter {

    private ShowStickersView mView;

    @Inject
    public ShowStickersFragmentPresenterImpl() {

    }

    @Override
    public void init(ShowStickersView view) {
        mView = view;
    }
}
