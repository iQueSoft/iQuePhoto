package net.iquesoft.iquephoto.view;

import net.iquesoft.iquephoto.model.GalleryImage;

import java.util.List;

/**
 * Created by Sergey on 9/7/2016.
 */
public interface IGalleryImagesFragmentView {

    void setupAdapter(List<GalleryImage> imagesList);
}
