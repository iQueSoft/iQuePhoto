package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IFrameFragmentView;

import javax.inject.Inject;

public class FramesFragmentPresenterImpl implements IFramesFragmentPresenter {

    private IFrameFragmentView mView;

    @Inject
    public FramesFragmentPresenterImpl() {

    }

    @Override
    public void init(IFrameFragmentView view) {
        mView = view;
    }
}
