package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IFrameFragmentView;

import javax.inject.Inject;

public class FrameFragmentPresenterImpl implements IFrameFragmentPresenter {

    private IFrameFragmentView mView;

    @Inject
    public FrameFragmentPresenterImpl() {

    }

    @Override
    public void init(IFrameFragmentView view) {
        mView = view;
    }
}
