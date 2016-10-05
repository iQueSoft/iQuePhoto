package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.model.Tool;

public interface IEditorActivityPresenter {

    /**
     * Initialise the application tools box.
     */
    void createToolsBox();

    void changeTool(Tool tool);

    void saveImage(Bitmap bitmap);

    void onBackPressed(Bitmap bitmap, Bitmap alteredBitmap);
}
