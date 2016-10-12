package net.iquesoft.iquephoto.view;

import net.iquesoft.iquephoto.model.ImageGallery;

import java.util.List;

public interface IGalleryImagesFragmentView {

    void setupAdapter(List<ImageGallery> imagesList);
}
