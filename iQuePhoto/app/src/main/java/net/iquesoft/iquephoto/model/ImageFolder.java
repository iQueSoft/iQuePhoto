package net.iquesoft.iquephoto.model;

import java.util.ArrayList;

public class ImageFolder {

    private String mFolderName;
    private ArrayList<Image> mImages;

    public ImageFolder(String folderName) {
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
