package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IAdjustmentFragmentView;

import javax.inject.Inject;

public class AdjustFragmentPresenterImpl implements IAdjustFragmentPresenter {

    private IAdjustmentFragmentView view;

    @Inject
    public AdjustFragmentPresenterImpl() {

    }

    @Override
    public void init(IAdjustmentFragmentView view) {
        this.view = view;
    }
}
