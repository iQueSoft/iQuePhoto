package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.presentation.view.editor.tools.AdjustView;

import javax.inject.Inject;

public class AdjustFragmentPresenterImpl implements IAdjustFragmentPresenter {

    private AdjustView mView;

    @Inject
    public AdjustFragmentPresenterImpl() {

    }

    @Override
    public void init(AdjustView view) {
        mView = view;
    }
}
