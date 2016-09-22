package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IDrawingFragmentView;

import javax.inject.Inject;

public class DrawingFragmentPresenterImpl implements IDrawingFragmentPresenter {

    private IDrawingFragmentView view;

    @Inject
    public DrawingFragmentPresenterImpl() {

    }

    @Override
    public void init(IDrawingFragmentView view) {
        this.view = view;
    }
}
