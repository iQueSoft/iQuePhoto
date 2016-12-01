package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.ImageAlbum;

import java.util.List;

public interface GalleryAlbumsView extends MvpView {
    void showNoImages();

    void setupAdapter(List<ImageAlbum> imageAlbums);
}
