package net.iquesoft.iquephoto.presentation.views.activity;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.ImageAlbum;

public interface GalleryView extends MvpView {
    void showImages(ImageAlbum imageAlbum);
}
