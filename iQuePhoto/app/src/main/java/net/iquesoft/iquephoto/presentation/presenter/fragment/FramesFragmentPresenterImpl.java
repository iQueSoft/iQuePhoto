package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.FramesView;

import javax.inject.Inject;

public class FramesFragmentPresenterImpl implements FramesPresenter {

    private FramesView mView;

    @Inject
    public FramesFragmentPresenterImpl() {

    }

    @Override
    public void init(FramesView view) {
        mView = view;
    }
}
