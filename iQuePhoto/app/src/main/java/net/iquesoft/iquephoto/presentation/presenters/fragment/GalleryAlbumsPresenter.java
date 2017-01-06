package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.presentation.views.fragment.GalleryAlbumsView;
import net.iquesoft.iquephoto.task.ImageLoader;

@InjectViewState
public class GalleryAlbumsPresenter extends MvpPresenter<GalleryAlbumsView> {

    public void fetchImages(Context context) {
        ImageLoader imageLoader = new ImageLoader(context);
        imageLoader.execute();
        
        imageLoader.setOnImageLoadedListener(albums -> {
            if (albums != null) {
                getViewState().setupAdapter(albums);
            } else {
                getViewState().showNoImages();
            }
        });
    }
}
