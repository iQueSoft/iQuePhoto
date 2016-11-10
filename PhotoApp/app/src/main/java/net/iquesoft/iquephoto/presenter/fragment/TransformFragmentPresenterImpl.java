package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.ITransformFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.ITransformFragmentView;

import javax.inject.Inject;

public class TransformFragmentPresenterImpl implements ITransformFragmentPresenter {

    private ITransformFragmentView mView;

    @Inject
    public TransformFragmentPresenterImpl() {

    }

    @Override
    public void init(ITransformFragmentView view) {
        mView = view;
    }
}
