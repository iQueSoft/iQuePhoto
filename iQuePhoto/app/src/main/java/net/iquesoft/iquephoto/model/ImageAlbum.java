package net.iquesoft.iquephoto.model;

import java.util.ArrayList;

public class ImageAlbum {

    private String mFolderName;
    private ArrayList<Image> mImages;

    public ImageAlbum(String folderName) {
        mFolderName = folderName;
        mImages = new ArrayList<>();
    }

    public String getFolderName() {
        return mFolderName;
    }

    public ArrayList<Image> getImages() {
        return mImages;
    }
}
