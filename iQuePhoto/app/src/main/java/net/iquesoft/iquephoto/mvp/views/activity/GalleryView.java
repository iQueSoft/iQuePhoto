package net.iquesoft.iquephoto.mvp.views.activity;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Image;

import java.util.List;

public interface GalleryView extends MvpView {
    void showImages(String albumName);

    void setAlbumImages(List<Image> images);

    void showHaveNoImages();

    void editImage(String imagePath);
}
