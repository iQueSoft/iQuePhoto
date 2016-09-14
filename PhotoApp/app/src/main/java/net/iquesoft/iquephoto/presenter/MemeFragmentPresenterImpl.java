package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IMemeFragmentView;

import javax.inject.Inject;

/**
 * Created by Sergey on 9/14/2016.
 */
public class MemeFragmentPresenterImpl implements IMemeFragmentPresenter {

    private IMemeFragmentView view;

    @Inject
    public MemeFragmentPresenterImpl() {

    }

    @Override
    public void init(IMemeFragmentView view) {
        this.view = view;
    }
}
