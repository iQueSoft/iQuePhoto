package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Image;

import java.util.List;

public interface GalleryImagesView extends MvpView {
    void setupAdapter(List<Image> images);

    void editImage(String imagePath);
}
