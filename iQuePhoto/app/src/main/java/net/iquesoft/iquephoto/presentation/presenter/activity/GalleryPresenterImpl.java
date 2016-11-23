package net.iquesoft.iquephoto.presentation.presenter.activity;

import android.support.v7.widget.RecyclerView;

import net.iquesoft.iquephoto.presentation.presenter.activity.interfaces.GalleryPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.GalleryView;

import javax.inject.Inject;

public class GalleryPresenterImpl implements GalleryPresenter {

    private GalleryView mView;

    @Inject
    public GalleryPresenterImpl(GalleryView view) {
        mView = view;
    }

    @Override
    public void onBackPressed(RecyclerView recyclerView) {
        /* TODO: Object object = recyclerView.getAdapter();

        if (object != null) {
            String classSimpleName = object.getClass().getSimpleName();
            if (classSimpleName.equalsIgnoreCase(ImageAlbumsAdapter.class.getSimpleName()))
                mView.navigateBack();
            else
                mView.showFolders();
        } else
            mView.navigateBack();*/
    }
}
