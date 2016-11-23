package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.GalleryImagesPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.GalleryView;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryImagesView;

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
        mView.setupAdapter();
    }

    @Override
    public void setImageForEditing(String imagePath) {
        mGalleryView.editImage(imagePath);
    }
}
