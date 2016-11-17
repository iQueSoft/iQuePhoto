package net.iquesoft.iquephoto.model;

public class Image {
    private int mId;
    private String mPath;

    public Image(int id, String path) {
        mId = id;
        mPath = path;
    }

    public int getId() {
        return mId;
    }

    public String getPath() {
        return mPath;
    }
}
