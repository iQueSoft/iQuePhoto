package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.model.Tool;

public interface IEditorActivityPresenter {
    void onBackPressed(Bitmap bitmap, Bitmap alteredBitmap);
}
