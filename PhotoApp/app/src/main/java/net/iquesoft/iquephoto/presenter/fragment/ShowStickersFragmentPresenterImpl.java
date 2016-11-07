package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.IShowStickersFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IShowStickersFragmentView;

import javax.inject.Inject;

public class ShowStickersFragmentPresenterImpl implements IShowStickersFragmentPresenter {

    private IShowStickersFragmentView mView;

    @Inject
    public ShowStickersFragmentPresenterImpl() {

    }

    @Override
    public void init(IShowStickersFragmentView view) {
        mView = view;
    }
}
