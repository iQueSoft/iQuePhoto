package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.presentation.view.editor.tools.StickersView;

import javax.inject.Inject;

public class StickersFragmentPresenterImpl implements IStickersFragmentPresenter {

    private StickersView mView;

    @Inject
    public StickersFragmentPresenterImpl() {

    }

    @Override
    public void init(StickersView view) {
        mView = view;
    }
}
