package net.iquesoft.iquephoto.model;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.utils.ImageHelper;

public class GalleryImage {
    private int id;
    private String path;
    private Bitmap bitmap;

    public GalleryImage(int id, String path) {
        this.id = id;
        this.path = path;
//        this.bitmap = ImageHelper.getBitmap(path);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        this.bitmap = ImageHelper.getBitmap(path);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
