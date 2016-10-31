package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IAdjustmentFragmentView;

import javax.inject.Inject;

public class AdjustmentFragmentPresenterImpl implements IAdjustmentFragmentPresenter {

    private IAdjustmentFragmentView view;

    @Inject
    public AdjustmentFragmentPresenterImpl() {

    }

    @Override
    public void init(IAdjustmentFragmentView view) {
        this.view = view;
    }
}
