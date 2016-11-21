package net.iquesoft.iquephoto.presentation.presenter.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import net.iquesoft.iquephoto.adapter.ImageAlbumsAdapter;
import net.iquesoft.iquephoto.model.ImageAlbum;
import net.iquesoft.iquephoto.presentation.presenter.activity.interfaces.GalleryPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.GalleryView;
import net.iquesoft.iquephoto.task.ImageLoader;

import javax.inject.Inject;

public class GalleryPresenterImpl implements GalleryPresenter {

    private GalleryView mView;

    @Inject
    public GalleryPresenterImpl(GalleryView view) {
        mView = view;
    }

    @Override
    public void fetchImages(Context context) {
        ImageLoader imageLoader = new ImageLoader(context);
        imageLoader.execute();

        imageLoader.setOnImageLoadedListener(folders -> {
            if (folders != null)
                mView.setupFoldersAdapter(folders);
            else
                mView.showHaveNoImages();
        });
    }

    @Override
    public void folderClicked(ImageAlbum imageAlbum) {
        mView.showImages(imageAlbum.getFolderName(), imageAlbum.getImages());
    }

    @Override
    public void onBackPressed(RecyclerView recyclerView) {
        Object object = recyclerView.getAdapter();

        if (object != null) {
            String classSimpleName = object.getClass().getSimpleName();
            if (classSimpleName.equalsIgnoreCase(ImageAlbumsAdapter.class.getSimpleName()))
                mView.navigateBack();
            else
                mView.showFolders();
        } else
            mView.navigateBack();
    }
}
