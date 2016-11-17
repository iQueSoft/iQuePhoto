package net.iquesoft.iquephoto.presentation.presenter.editor;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.model.Tool;

public interface IEditorActivityPresenter {
    void onBackPressed(Bitmap bitmap, Bitmap alteredBitmap);
}
