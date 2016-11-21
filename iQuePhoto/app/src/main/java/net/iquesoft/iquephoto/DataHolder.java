package net.iquesoft.iquephoto;

import android.graphics.Bitmap;
import android.net.Uri;

import net.iquesoft.iquephoto.core.editor.ImageEditorView;

public class DataHolder {
    private Bitmap mBitmap;

    private Bitmap mShareBitmap;

    private ImageEditorView editorView;

    private Uri mImageUri;

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

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public Uri getImageUri() {
        return mImageUri;
    }

    public void setImageUri(Uri imageUri) {
        mImageUri = imageUri;
    }
}
