package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.IDrawingFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IDrawingFragmentView;

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
