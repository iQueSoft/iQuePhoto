package net.iquesoft.iquephoto;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.core.EditorImageView;

public class DataHolder {
    private Bitmap mShareBitmap;

    private EditorImageView editorView;

    private static DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }

    public EditorImageView getEditorView() {
        return editorView;
    }

    public void setEditorView(EditorImageView editorView) {
        this.editorView = editorView;
    }

    public Bitmap getShareBitmap() {
        return mShareBitmap;
    }

    public void setShareBitmap(Bitmap shareBitmap) {
        mShareBitmap = shareBitmap;
    }
}
