package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.presentation.view.editor.tools.ShowStickersView;

import javax.inject.Inject;

public class ShowStickersFragmentPresenterImpl implements IShowStickersFragmentPresenter {

    private ShowStickersView mView;

    @Inject
    public ShowStickersFragmentPresenterImpl() {

    }

    @Override
    public void init(ShowStickersView view) {
        mView = view;
    }
}
