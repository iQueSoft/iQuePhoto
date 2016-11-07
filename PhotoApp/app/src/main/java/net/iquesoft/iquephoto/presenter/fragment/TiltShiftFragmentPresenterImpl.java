package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.ITiltShiftFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.ITiltShiftFragmentView;

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
