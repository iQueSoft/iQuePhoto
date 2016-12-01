package net.iquesoft.iquephoto.mvp.presenters.fragment;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.models.Image;
import net.iquesoft.iquephoto.mvp.views.fragment.GalleryImagesView;
import net.iquesoft.iquephoto.ui.fragments.GalleryImagesFragment;

@InjectViewState
public class GalleryImagesPresenter extends MvpPresenter<GalleryImagesView> {

    public void setupAlbumImages(Bundle bundle) {
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
