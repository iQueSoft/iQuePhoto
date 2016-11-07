package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.IFramesFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IFramesFragmentView;

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
