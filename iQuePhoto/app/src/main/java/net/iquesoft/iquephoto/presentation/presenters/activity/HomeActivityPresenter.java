package net.iquesoft.iquephoto.presentation.presenters.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.tbruyelle.rxpermissions.RxPermissions;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.presentation.views.activity.HomeView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static net.iquesoft.iquephoto.ui.activities.HomeActivity.REQ_CAMERA;

@InjectViewState
public class HomeActivityPresenter extends MvpPresenter<HomeView> {
    private String mCurrentPhotoPath;

    public void openCamera(@NonNull Context context) {
        checkPermission(context, CAMERA);
    }

    public void openGallery(@NonNull Context context) {
        checkPermission(context, READ_EXTERNAL_STORAGE);
    }

    public void startEditing(int requestCode, int resultCode) {
        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) {
            getViewState().startEditing(mCurrentPhotoPath);
        }
    }

    private void checkPermission(Context context, String permission) {
        RxPermissions.getInstance(context)
                .request(permission)
                .subscribe(granted -> {
                            if (granted) {
                                switch (permission) {
                                    case CAMERA:
                                        startCamera(context);
                                        break;
                                    case READ_EXTERNAL_STORAGE:
                                        getViewState().startGallery();
                                        break;
                                }
                            } else {
                                switch (permission) {
                                    case CAMERA:
                                        getViewState()
                                                .showPermissionDenied(R.string.camera_permission);
                                        break;
                                    case READ_EXTERNAL_STORAGE:
                                        getViewState()
                                                .showPermissionDenied(R.string.read_storage_permission);
                                        break;
                                }
                            }
                        }
                );
    }

    private void startCamera(Context context) {
        File photoFile = null;
        try {
            photoFile = createImageFile(context);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(context,
                    "net.iquesoft.iquephoto.provider",
                    photoFile);

            getViewState().startCamera(photoURI);
        }
    }

    private File createImageFile(Context context) throws IOException {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }
}