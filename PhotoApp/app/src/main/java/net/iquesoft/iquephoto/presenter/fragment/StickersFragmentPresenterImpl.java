package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.IStickersFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IStickersFragmentView;

import javax.inject.Inject;

public class StickersFragmentPresenterImpl implements IStickersFragmentPresenter {

    private IStickersFragmentView mView;

    @Inject
    public StickersFragmentPresenterImpl() {

    }

    @Override
    public void init(IStickersFragmentView view) {
        mView = view;
    }
}
