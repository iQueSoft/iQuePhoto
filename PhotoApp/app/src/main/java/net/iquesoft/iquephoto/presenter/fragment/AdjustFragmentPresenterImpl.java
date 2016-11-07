package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.IAdjustFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IAdjustFragmentView;

import javax.inject.Inject;

public class AdjustFragmentPresenterImpl implements IAdjustFragmentPresenter {

    private IAdjustFragmentView mView;

    @Inject
    public AdjustFragmentPresenterImpl() {

    }

    @Override
    public void init(IAdjustFragmentView view) {
        mView = view;
    }
}
