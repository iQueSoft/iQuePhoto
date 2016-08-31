package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;

import net.iquesoft.iquephoto.model.Tool;

import javax.microedition.khronos.opengles.GL10;

public interface IMainActivityPresenter {

    /**
     * Initialise the application tools box.
     */
    void createToolsBox();

    void changeTool(Tool tool);

    void onBackPressed();

    void saveImage(Bitmap bitmap);

    void shareToInstagram(Bitmap bitmap);
}
