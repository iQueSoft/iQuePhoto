package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IRotationFragmentView;

import javax.inject.Inject;

public class RotationFragmentPresenterImpl implements IRotationFragmentPresenter {

    private IRotationFragmentView view;

    @Inject
    public RotationFragmentPresenterImpl() {
    }

    @Override
    public void init(IRotationFragmentView view) {
        this.view = view;
    }

}
