package net.iquesoft.iquephoto.presentation.views.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpView;

public interface EditorActivityView extends MvpView {

    void startEditing(Bitmap bitmap);

    void showLoading();

    void hideLoading();

    void showAlertDialog();

    void showToastMessage(int stringResource);

    void navigateBack(boolean isFragment);

    void share(@NonNull Uri uri, @Nullable String packageName);
}