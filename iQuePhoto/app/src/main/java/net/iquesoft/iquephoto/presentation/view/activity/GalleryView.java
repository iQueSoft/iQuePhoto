package net.iquesoft.iquephoto.presentation.view.activity;

import net.iquesoft.iquephoto.model.Image;
import net.iquesoft.iquephoto.model.ImageAlbum;

import java.util.List;

public interface GalleryView {
    void setupFoldersAdapter(List<ImageAlbum> imageAlba);

    void showImages(String folderName, List<Image> images);

    void showHaveNoImages();

    void showFolders();

    void navigateBack();
}
