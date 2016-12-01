package net.iquesoft.iquephoto.mvp.views.activity;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;

public interface EditorView extends MvpView {

    void showAlertDialog();

    void showToastMessage(int stringResource);

    void navigateBack(boolean isFragment);
}
