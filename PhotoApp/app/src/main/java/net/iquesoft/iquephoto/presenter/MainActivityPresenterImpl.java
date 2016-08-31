package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ToolsAdapter;
import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.view.IMainActivityView;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.IntBuffer;
import java.util.Random;

import javax.inject.Inject;
import javax.microedition.khronos.opengles.GL10;

public class MainActivityPresenterImpl implements IMainActivityPresenter {

    private final static String IMAGE_STORAGE_PATH = Environment.getExternalStorageDirectory().toString() + "/" +
            android.os.Environment.DIRECTORY_DCIM + "/iQuePhoto";

    private IMainActivityView view;

    @Inject
    public MainActivityPresenterImpl(IMainActivityView view) {
        this.view = view;
    }

    @Override
    public void createToolsBox() {
        ToolsAdapter toolsAdapter = new ToolsAdapter(Tool.getToolsList());
        view.initTools(toolsAdapter);
    }

    @Override
    public void changeTool(Tool tool) {
        view.changeTool(tool);
    }

    @Override
    public void onBackPressed() {
        view.showAlertDialog();
    }

    @Override
    public void saveImage(Bitmap bitmap) {
        File myDir = new File(IMAGE_STORAGE_PATH);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            view.showToastMessage(R.string.successfully_saved);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shareToInstagram(Bitmap bitmap) {

    }
}
