package net.iquesoft.iquephoto.model;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.utils.ImageHelper;

public class ImageGallery {
    private int mId;
    private String path;
    private Bitmap mBitmap;

    public ImageGallery(int id, String path) {
        mId = id;
        this.path = path;
        mBitmap = ImageHelper.getBitmap(path);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        mBitmap = ImageHelper.getBitmap(path);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }
}
