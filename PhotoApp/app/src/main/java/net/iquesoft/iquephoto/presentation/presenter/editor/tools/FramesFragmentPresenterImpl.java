package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.presentation.view.editor.tools.FramesView;

import javax.inject.Inject;

public class FramesFragmentPresenterImpl implements IFramesFragmentPresenter {

    private FramesView mView;

    @Inject
    public FramesFragmentPresenterImpl() {

    }

    @Override
    public void init(FramesView view) {
        mView = view;
    }
}
