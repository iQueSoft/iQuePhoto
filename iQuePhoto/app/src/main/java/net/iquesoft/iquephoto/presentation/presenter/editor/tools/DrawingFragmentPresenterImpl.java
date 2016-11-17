package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.presentation.view.editor.tools.DrawingView;

import javax.inject.Inject;

public class DrawingFragmentPresenterImpl implements IDrawingFragmentPresenter {

    private DrawingView view;

    @Inject
    public DrawingFragmentPresenterImpl() {

    }

    @Override
    public void init(DrawingView view) {
        this.view = view;
    }
}
