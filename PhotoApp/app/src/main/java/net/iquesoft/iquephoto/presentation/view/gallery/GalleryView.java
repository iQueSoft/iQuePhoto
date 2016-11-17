package net.iquesoft.iquephoto.presentation.view.gallery;

import net.iquesoft.iquephoto.model.Image;
import net.iquesoft.iquephoto.model.ImageFolder;

import java.util.List;

public interface GalleryView {
    void setupFoldersAdapter(List<ImageFolder> imageFolders);

    void showImages(String folderName, List<Image> images);

    void showHaveNoImages();

    void showFolders();

    void navigateBack();
}
