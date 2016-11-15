package net.iquesoft.iquephoto.view.activity.interfaces;

import net.iquesoft.iquephoto.model.Image;
import net.iquesoft.iquephoto.model.ImageFolder;

import java.util.List;

public interface IGalleryActivityView {
    void setupFoldersAdapter(List<ImageFolder> imageFolders);

    void showImages(String folderName, List<Image> images);

    void showHaveNoImages();

    void showFolders();

    void navigateBack();
}
