package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IStickersFragmentView;

import javax.inject.Inject;

/**
 * Created by Sergey on 9/1/2016.
 */
public class StickersFragmentPresenterImpl implements IStickersFragmentPresenter {

    private IStickersFragmentView view;

    @Inject
    public StickersFragmentPresenterImpl() {

    }

    @Override
    public void init(IStickersFragmentView view) {
        this.view = view;
    }
}
