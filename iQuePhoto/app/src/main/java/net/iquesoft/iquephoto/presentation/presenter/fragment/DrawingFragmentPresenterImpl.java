package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.DrawingView;

import javax.inject.Inject;

public class DrawingFragmentPresenterImpl implements DrawingPresenter {

    private DrawingView view;

    @Inject
    public DrawingFragmentPresenterImpl() {

    }

    @Override
    public void init(DrawingView view) {
        this.view = view;
    }
}
