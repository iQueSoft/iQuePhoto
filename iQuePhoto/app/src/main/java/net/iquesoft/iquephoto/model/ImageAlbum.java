package net.iquesoft.iquephoto.model;

import java.util.ArrayList;

public class ImageAlbum {

    private String mName;
    private ArrayList<Image> mImages;

    public ImageAlbum(String name) {
        mName = name;
        mImages = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public ArrayList<Image> getImages() {
        return mImages;
    }
}
