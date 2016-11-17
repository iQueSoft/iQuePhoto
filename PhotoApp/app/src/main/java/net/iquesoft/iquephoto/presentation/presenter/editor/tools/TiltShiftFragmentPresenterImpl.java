package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.presentation.view.editor.tools.TiltShiftView;

import javax.inject.Inject;

public class TiltShiftFragmentPresenterImpl implements ITiltShiftFragmentPresenter {
    private TiltShiftView mView;

    @Inject
    public TiltShiftFragmentPresenterImpl() {

    }

    @Override
    public void init(TiltShiftView view) {
        mView = view;
    }
}
