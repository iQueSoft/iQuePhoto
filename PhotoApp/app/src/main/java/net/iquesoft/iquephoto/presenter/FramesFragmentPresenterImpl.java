package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IFramesFragmentView;

import javax.inject.Inject;

public class FramesFragmentPresenterImpl implements IFramesFragmentPresenter {

    private IFramesFragmentView mView;

    @Inject
    public FramesFragmentPresenterImpl() {

    }

    @Override
    public void init(IFramesFragmentView view) {
        mView = view;
    }
}
