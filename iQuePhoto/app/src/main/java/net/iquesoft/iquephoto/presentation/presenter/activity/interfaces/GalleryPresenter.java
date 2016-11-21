package net.iquesoft.iquephoto.presentation.presenter.activity.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import net.iquesoft.iquephoto.model.ImageAlbum;

public interface GalleryPresenter {
    void fetchImages(Context context);

    void folderClicked(ImageAlbum imageAlbum);

    void onBackPressed(RecyclerView recyclerView);
}
