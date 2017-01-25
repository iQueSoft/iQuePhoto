package net.iquesoft.iquephoto.presentation.views.activity;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

public interface EditorView extends MvpView {

    void setupEditImage(Bitmap bitmap);

    void showAlertDialog();

    void showToastMessage(int stringResource);

    void navigateBack(boolean isFragment);
}
