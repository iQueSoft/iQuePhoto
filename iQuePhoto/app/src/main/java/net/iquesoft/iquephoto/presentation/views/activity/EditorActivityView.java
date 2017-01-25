package net.iquesoft.iquephoto.presentation.views.activity;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

public interface EditorActivityView extends MvpView {

    void startEditing(Bitmap bitmap);

    void showAlertDialog();

    void showToastMessage(int stringResource);

    void navigateBack(boolean isFragment);
}