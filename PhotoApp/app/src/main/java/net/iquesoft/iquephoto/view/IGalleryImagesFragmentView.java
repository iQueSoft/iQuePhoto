package net.iquesoft.iquephoto.view;

import net.iquesoft.iquephoto.model.GalleryImage;

import java.util.List;

public interface IGalleryImagesFragmentView {

    void setupAdapter(List<GalleryImage> imagesList);
}
