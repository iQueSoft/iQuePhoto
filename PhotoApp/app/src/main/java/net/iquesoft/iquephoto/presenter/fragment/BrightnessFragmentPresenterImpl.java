package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.IBrightnessFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IBrightnessFragmentView;

import javax.inject.Inject;

public class BrightnessFragmentPresenterImpl implements IBrightnessFragmentPresenter {

    private IBrightnessFragmentView mView;

    @Inject
    public BrightnessFragmentPresenterImpl() {

    }

    @Override
    public void init(IBrightnessFragmentView view) {
        mView = view;
    }
}
