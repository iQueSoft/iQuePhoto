package net.iquesoft.iquephoto.model;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.utils.ImageHelper;

public class GalleryImage {
    private int mId;
    private String mPath;
    private Bitmap mBitmap;

    public GalleryImage(int id, String path) {
        mId = id;
        mPath = path;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }
}
