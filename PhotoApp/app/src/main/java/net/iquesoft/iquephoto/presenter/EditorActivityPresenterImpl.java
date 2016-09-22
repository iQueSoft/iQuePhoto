package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;
import android.os.Environment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ToolsAdapter;
import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.view.IEditorActivityView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.inject.Inject;

public class EditorActivityPresenterImpl implements IEditorActivityPresenter {

    private final static String IMAGE_STORAGE_PATH = Environment.getExternalStorageDirectory().toString() + "/" +
            android.os.Environment.DIRECTORY_DCIM + "/iQuePhoto";

    private IEditorActivityView view;

    @Inject
    public EditorActivityPresenterImpl(IEditorActivityView view) {
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
        String fname = "EditorImage-" + n + ".jpg";
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
