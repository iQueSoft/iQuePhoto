package net.iquesoft.iquephoto.presenter.activity.interfaces;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.model.Tool;

public interface IEditorActivityPresenter {
    void onBackPressed(Bitmap bitmap, Bitmap alteredBitmap);
}
