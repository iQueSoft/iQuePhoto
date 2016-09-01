package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IShowStickersFragmentView;

import javax.inject.Inject;

public class ShowStickersFragmentPresenterImpl implements IShowStickersFragmentPresenter {

    private IShowStickersFragmentView view;

    @Inject
    public ShowStickersFragmentPresenterImpl() {

    }

    @Override
    public void init(IShowStickersFragmentView view) {
        this.view = view;
    }
}
