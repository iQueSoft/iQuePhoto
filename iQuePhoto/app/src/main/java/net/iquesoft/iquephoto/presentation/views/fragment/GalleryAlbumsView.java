package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.ImageAlbum;

import java.util.List;

public interface GalleryAlbumsView extends MvpView {
    void showNoImages();

    void setupAdapter(List<ImageAlbum> imageAlbums);
}
