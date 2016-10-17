package net.iquesoft.iquephoto;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import net.iquesoft.iquephoto.core.ImageEditorView;

public class DataHolder {
    private Drawable mDrawable;

    private Bitmap mShareBitmap;

    private ImageEditorView editorView;

    private static DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }

    public ImageEditorView getEditorView() {
        return editorView;
    }

    public void setEditorView(ImageEditorView editorView) {
        this.editorView = editorView;
    }

    public Bitmap getShareBitmap() {
        return mShareBitmap;
    }

    public void setShareBitmap(Bitmap shareBitmap) {
        mShareBitmap = shareBitmap;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }
}
