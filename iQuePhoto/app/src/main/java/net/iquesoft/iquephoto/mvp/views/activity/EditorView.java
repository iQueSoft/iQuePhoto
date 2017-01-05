package net.iquesoft.iquephoto.mvp.views.activity;

import com.arellomobile.mvp.MvpView;

public interface EditorView extends MvpView {

    void showAlertDialog();

    void showToastMessage(int stringResource);

    void navigateBack(boolean isFragment);
}
