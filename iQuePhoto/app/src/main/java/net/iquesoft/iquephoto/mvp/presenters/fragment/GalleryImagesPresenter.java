package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.views.fragment.GalleryImagesView;

@InjectViewState
public class GalleryImagesPresenter extends MvpPresenter<GalleryImagesView> {

    public void setImageForEditing(String imagePath) {
        // TODO: mGalleryView.editImage(imagePath);
    }
}
