package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;
import android.os.Environment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.view.IEditorActivityView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.inject.Inject;

public class EditorActivityPresenterImpl implements IEditorActivityPresenter {

    private final static String IMAGE_STORAGE_PATH = Environment.getExternalStorageDirectory().toString() + "/" +
            android.os.Environment.DIRECTORY_DCIM + "/iQuePhoto";

    private IEditorActivityView mView;

    @Inject
    public EditorActivityPresenterImpl(IEditorActivityView view) {
        mView = view;
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

            mView.showToastMessage(R.string.successfully_saved);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(Bitmap bitmap, Bitmap alteredBitmap) {
        if (!bitmap.sameAs(alteredBitmap))
            mView.showAlertDialog();
        else mView.navigateBack();
    }
}
