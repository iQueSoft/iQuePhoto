package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.TransformView;

import javax.inject.Inject;

public class TransformFragmentPresenterImpl implements TransformPresenter {

    private TransformView mView;

    @Inject
    public TransformFragmentPresenterImpl() {

    }

    @Override
    public void init(TransformView view) {
        mView = view;
    }
}
