package net.iquesoft.iquephoto.presentation.views.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.isseiaoki.simplecropview.CropImageView;

public interface PreviewView extends MvpView {

    void setupImage(Bitmap bitmap);

    void onCropModeChanged(CropImageView.CropMode cropMode);

    void startEditingImage(Uri uri);

    void showProgress();

    void flipImage(Drawable drawable);

    void dismissProgress();

    void createTab(@StringRes int title, boolean selected);
}
