package net.iquesoft.iquephoto.models;

public class Font {
    private String mTitle;
    private String mPath;

    public Font(String title, String path) {
        mTitle = title;
        mPath = path;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPath() {
        return "fonts/" + mPath;
    }
}