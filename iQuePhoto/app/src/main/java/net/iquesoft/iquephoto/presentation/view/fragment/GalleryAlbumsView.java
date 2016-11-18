package net.iquesoft.iquephoto.presentation.view.fragment;

import net.iquesoft.iquephoto.model.ImageAlbum;

import java.util.List;

public interface GalleryAlbumsView {
    void setupAdapter(List<ImageAlbum> imageAlbums);
}
