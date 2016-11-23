package net.iquesoft.iquephoto.presentation.presenter.fragment;

import android.content.Context;

import net.iquesoft.iquephoto.model.ImageAlbum;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.GalleryAlbumsPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.GalleryView;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryAlbumsView;
import net.iquesoft.iquephoto.task.ImageLoader;

import java.util.List;

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
    public void fetchImages(Context context) {
        ImageLoader imageLoader = new ImageLoader(context);
        imageLoader.execute();

        imageLoader.setOnImageLoadedListener(albums -> {
            if (albums != null) {
                mView.setupAdapter(albums);
            } else {
                mGalleryView.showHaveNoImages();
            }
        });
    }

    @Override
    public void showAlbumImages(ImageAlbum imageAlbum) {
        mGalleryView.setAlbumImages(imageAlbum.getImages());
        mGalleryView.showImages(imageAlbum.getName());
    }
}
