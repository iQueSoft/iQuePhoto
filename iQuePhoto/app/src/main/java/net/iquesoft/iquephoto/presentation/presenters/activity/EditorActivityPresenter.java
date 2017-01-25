package net.iquesoft.iquephoto.presentation.presenters.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.presentation.views.activity.EditorView;

import java.io.IOException;

@InjectViewState
public class EditorActivityPresenter extends MvpPresenter<EditorView> {
    private Bitmap mBitmap;

    public EditorActivityPresenter(@NonNull Context context, @NonNull Intent intent) {
        try {
            mBitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), intent.getData()
            );

            getViewState().setupEditImage(mBitmap);
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
}