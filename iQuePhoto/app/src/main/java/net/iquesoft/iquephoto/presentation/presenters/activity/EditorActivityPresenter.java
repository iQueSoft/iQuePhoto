package net.iquesoft.iquephoto.presentation.presenters.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.presentation.views.activity.EditorActivityView;
import net.iquesoft.iquephoto.task.ImageCacheSaveTask;
import net.iquesoft.iquephoto.task.ImageSaveTask;
import net.iquesoft.iquephoto.utils.BitmapUtil;

import java.io.IOException;

@InjectViewState
public class EditorActivityPresenter extends MvpPresenter<EditorActivityView> {
    private Uri mUri;
    private Bitmap mBitmap;

    private Context mContext;

    public EditorActivityPresenter(@NonNull Context context, @NonNull Intent intent) {
        mContext = context;

        try {
            mUri = intent.getData();

            mBitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), mUri
            );

            BitmapUtil.logBitmapInfo("Cropped Bitmap", mBitmap);

            getViewState().startEditing(mBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed(@Nullable Bitmap alteredBitmap) {
        if (alteredBitmap != null) {
            if (!mBitmap.sameAs(alteredBitmap)) {
                getViewState().showAlertDialog();
            } else {
                getViewState().navigateBack(false);
            }
        } else {
            getViewState().navigateBack(false);
        }
    }

    public void save(@NonNull Bitmap bitmap) {
        new ImageSaveTask(mContext, bitmap).execute();
    }

    public void share(@NonNull Bitmap bitmap, @Nullable String packageName) {
        ImageCacheSaveTask imageCacheSaveTask = new ImageCacheSaveTask(mContext, bitmap);
        imageCacheSaveTask.setOnImageLoadedListener(new ImageCacheSaveTask.OnImageCacheSaveListener() {
            @Override
            public void onSaveStarted() {
                //getViewState().showLoading();
            }

            @Override
            public void onImageSaved(Uri uri) {
                getViewState().share(uri, packageName);
            }

            @Override
            public void onSaveFinished() {
                //getViewState().hideLoading();
            }
        });
        imageCacheSaveTask.execute();
    }
}