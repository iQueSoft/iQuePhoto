package net.iquesoft.iquephoto.presentation.presenter.activity.interfaces;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.model.Tool;

public interface EditorPresenter {
    void onBackPressed(Bitmap bitmap, Bitmap alteredBitmap);
}
