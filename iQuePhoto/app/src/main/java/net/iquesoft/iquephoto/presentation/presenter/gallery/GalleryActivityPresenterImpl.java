package net.iquesoft.iquephoto.presentation.presenter.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import net.iquesoft.iquephoto.adapters.ImageFoldersAdapter;
import net.iquesoft.iquephoto.model.ImageFolder;
import net.iquesoft.iquephoto.presentation.view.gallery.GalleryView;
import net.iquesoft.iquephoto.tasks.ImageLoader;

import javax.inject.Inject;

public class GalleryActivityPresenterImpl implements IGalleryActivityPresenter {

    private GalleryView mView;

    @Inject
    public GalleryActivityPresenterImpl(GalleryView view) {
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
    public void folderClicked(ImageFolder imageFolder) {
        mView.showImages(imageFolder.getFolderName(), imageFolder.getImages());
    }

    @Override
    public void onBackPressed(RecyclerView recyclerView) {
        Object object = recyclerView.getAdapter();

        if (object != null) {
            String classSimpleName = object.getClass().getSimpleName();
            if (classSimpleName.equalsIgnoreCase(ImageFoldersAdapter.class.getSimpleName()))
                mView.navigateBack();
            else
                mView.showFolders();
        } else
            mView.navigateBack();
    }
}
