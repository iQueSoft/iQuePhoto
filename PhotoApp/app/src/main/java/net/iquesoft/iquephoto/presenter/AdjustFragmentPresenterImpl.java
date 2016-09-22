package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IBrightnessFragmentView;

import javax.inject.Inject;

public class AdjustFragmentPresenterImpl implements IAdjustFragmentPresenter {

    private IBrightnessFragmentView view;

    @Inject
    public AdjustFragmentPresenterImpl() {

    }

    @Override
    public void init(IBrightnessFragmentView view) {
        this.view = view;
    }
}
