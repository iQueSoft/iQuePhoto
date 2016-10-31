package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.ITiltShiftFragmentView;

import javax.inject.Inject;

public class TiltShiftFragmentPresenterImpl implements ITiltShiftFragmentPresenter {
    private ITiltShiftFragmentView mView;

    @Inject
    public TiltShiftFragmentPresenterImpl() {

    }

    @Override
    public void init(ITiltShiftFragmentView view) {
        mView = view;
    }
}
