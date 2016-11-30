package net.iquesoft.iquephoto.mvp.views.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

public interface PreviewView extends MvpView {

    void startEditingImage(Uri uri);

    void showProgress();

    void flipImage(Drawable drawable);

    void dismissProgress();

    void createTab(@StringRes int title, boolean selected);
}
