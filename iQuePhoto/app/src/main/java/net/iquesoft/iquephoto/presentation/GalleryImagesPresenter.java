package net.iquesoft.iquephoto.presentation;

import javax.inject.Inject;

public class GalleryImagesPresenter implements GalleryImagesContract.Presenter {

    private final GalleryImagesContract.View mView;

    @Inject
    public GalleryImagesPresenter(GalleryImagesContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
