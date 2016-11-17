package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.presentation.view.editor.tools.TransformView;

import javax.inject.Inject;

public class TransformFragmentPresenterImpl implements ITransformFragmentPresenter {

    private TransformView mView;

    @Inject
    public TransformFragmentPresenterImpl() {

    }

    @Override
    public void init(TransformView view) {
        mView = view;
    }
}
