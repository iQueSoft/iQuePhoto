package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.model.ImageAlbum;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryAlbumsView;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryImagesView;

public interface GalleryAlbumsPresenter extends BaseFragmentPresenter<GalleryAlbumsView> {
    void showAlbumImages(ImageAlbum imageAlbum);
}
