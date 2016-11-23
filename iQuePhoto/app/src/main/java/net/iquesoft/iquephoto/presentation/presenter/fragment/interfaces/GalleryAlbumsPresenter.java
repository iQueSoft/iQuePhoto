package net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces;

import android.content.Context;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.model.ImageAlbum;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryAlbumsView;

public interface GalleryAlbumsPresenter extends BaseFragmentPresenter<GalleryAlbumsView> {
    void fetchImages(Context context);

    void showAlbumImages(ImageAlbum imageAlbum);
}
