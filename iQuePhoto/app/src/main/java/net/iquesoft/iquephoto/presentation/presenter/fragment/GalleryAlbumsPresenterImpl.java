package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.model.ImageAlbum;
import net.iquesoft.iquephoto.presentation.view.activity.GalleryView;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryAlbumsView;

import javax.inject.Inject;

public class GalleryAlbumsPresenterImpl implements GalleryAlbumsPresenter {

    private GalleryAlbumsView mView;
    private GalleryView mGalleryView;

    @Inject
    public GalleryAlbumsPresenterImpl(GalleryView galleryView) {
        mGalleryView = galleryView;
    }

    @Override
    public void init(GalleryAlbumsView view) {
        mView = view;
    }

    @Override
    public void showAlbumImages(ImageAlbum imageAlbum) {
        
    }
}
