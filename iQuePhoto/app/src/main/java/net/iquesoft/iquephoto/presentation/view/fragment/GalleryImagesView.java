package net.iquesoft.iquephoto.presentation.view.fragment;

import net.iquesoft.iquephoto.model.Image;

import java.util.List;

public interface GalleryImagesView {
    void setupAdapter(List<Image> images);
}
