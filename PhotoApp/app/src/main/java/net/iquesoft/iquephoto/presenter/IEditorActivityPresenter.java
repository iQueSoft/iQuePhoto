package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.model.Tool;

public interface IEditorActivityPresenter {
    void saveImage(Bitmap bitmap);

    void onBackPressed(Bitmap bitmap, Bitmap alteredBitmap);
}
