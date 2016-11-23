package net.iquesoft.iquephoto.presentation.view.activity;

import net.iquesoft.iquephoto.model.Image;

import java.util.List;

public interface GalleryView {
    void showImages(String albumName);

    void setAlbumImages(List<Image> images);

    void showHaveNoImages();

    void editImage(String imagePath);
}
