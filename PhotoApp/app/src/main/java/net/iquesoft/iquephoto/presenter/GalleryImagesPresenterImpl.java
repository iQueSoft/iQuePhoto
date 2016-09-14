package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.IGalleryImagesFragmentView;

import javax.inject.Inject;

/**
 * Created by Sergey on 9/7/2016.
 */
public class GalleryImagesPresenterImpl implements IGalleryImagesFragmentPresenter {

    IGalleryImagesFragmentView view;

    @Inject
    public GalleryImagesPresenterImpl() {

    }

    @Override
    public void init(IGalleryImagesFragmentView view) {
        this.view = view;
    }
}
