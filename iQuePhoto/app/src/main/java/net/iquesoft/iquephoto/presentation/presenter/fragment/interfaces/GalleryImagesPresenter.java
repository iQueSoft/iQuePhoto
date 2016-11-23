package net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.model.ImageAlbum;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryImagesView;

public interface GalleryImagesPresenter extends BaseFragmentPresenter<GalleryImagesView> {
    void setImageForEditing(String imagePath);
}
