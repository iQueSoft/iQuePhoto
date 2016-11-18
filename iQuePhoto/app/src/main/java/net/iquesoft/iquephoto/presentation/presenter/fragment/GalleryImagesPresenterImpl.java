package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.view.activity.GalleryView;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryAlbumsView;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryImagesView;
import net.iquesoft.iquephoto.presentation.view.fragment.OverlayView;

import javax.inject.Inject;

public class GalleryImagesPresenterImpl implements GalleryImagesPresenter {

    private GalleryImagesView mView;
    private GalleryView mGalleryView;

    @Inject
    public GalleryImagesPresenterImpl(GalleryView galleryView) {
        mGalleryView = galleryView;
    }

    @Override
    public void init(GalleryImagesView view) {
        mView = view;
    }
}
