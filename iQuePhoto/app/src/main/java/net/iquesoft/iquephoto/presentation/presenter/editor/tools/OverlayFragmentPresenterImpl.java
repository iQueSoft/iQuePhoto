package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.presentation.view.editor.tools.OverlayView;

import javax.inject.Inject;

public class OverlayFragmentPresenterImpl implements IOverlayFragmentPresenter {

    private OverlayView mView;

    @Inject
    public OverlayFragmentPresenterImpl() {

    }

    @Override
    public void init(OverlayView view) {
        mView = view;
    }
}
