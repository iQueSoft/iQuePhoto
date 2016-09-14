package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.ICropFragmentView;

import javax.inject.Inject;

/**
 * Created by Sergey on 9/12/2016.
 */
public class CropFragmentPresenterImpl implements ICropFragmentPresenter {

    private ICropFragmentView view;

    @Inject
    public CropFragmentPresenterImpl() {

    }

    @Override
    public void init(ICropFragmentView view) {
        this.view = view;
    }
}
