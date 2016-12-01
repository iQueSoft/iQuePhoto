package net.iquesoft.iquephoto.mvp.views.activity;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Image;
import net.iquesoft.iquephoto.mvp.models.ImageAlbum;

import java.util.List;

public interface GalleryView extends MvpView {
    void showImages(ImageAlbum imageAlbum);

    void editImage(String imagePath);
}
