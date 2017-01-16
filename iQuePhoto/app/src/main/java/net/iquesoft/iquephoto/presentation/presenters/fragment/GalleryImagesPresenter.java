package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.models.Image;
import net.iquesoft.iquephoto.presentation.views.fragment.GalleryImagesView;
import net.iquesoft.iquephoto.ui.fragments.GalleryImagesFragment;

@InjectViewState
public class GalleryImagesPresenter extends MvpPresenter<GalleryImagesView> {

    public GalleryImagesPresenter(@NonNull Bundle bundle) {
        setupAlbumImages(bundle);
    }

    private void setupAlbumImages(Bundle bundle) {
        if (bundle != null) {
            getViewState().setupAdapter(
                    bundle.getParcelableArrayList(GalleryImagesFragment.ARG_PARAM)
            );
        }
    }

    public void setImageForEdit(Image image) {
        getViewState().editImage(image.getPath());
    }


}
