package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IBrushFragmentView;

import javax.inject.Inject;

public class BrushFragmentPresenterImpl implements IBrushFragmentPresenter {

    private IBrushFragmentView view;

    @Inject
    public BrushFragmentPresenterImpl() {

    }

    @Override
    public void init(IBrushFragmentView view) {
        this.view = view;
    }
}
