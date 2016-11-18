package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.fragment.TiltShiftView;

import javax.inject.Inject;

public class TiltShiftFragmentPresenterImpl implements TiltShiftPresenter {
    private TiltShiftView mView;

    @Inject
    public TiltShiftFragmentPresenterImpl() {

    }

    @Override
    public void init(TiltShiftView view) {
        mView = view;
    }
}
