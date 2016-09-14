package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IDrawFragmentView;

import javax.inject.Inject;

public class DrawFragmentPresenterImpl implements IDrawFragmentPresenter {

    private IDrawFragmentView view;

    @Inject
    public DrawFragmentPresenterImpl() {

    }

    @Override
    public void init(IDrawFragmentView view) {
        this.view = view;
    }
}
