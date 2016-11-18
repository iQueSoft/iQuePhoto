package net.iquesoft.iquephoto.presentation.view.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.StringRes;

public interface PreviewView {

    void startEditingImage(Uri uri);

    void showProgress();

    void flipImage(Drawable drawable);

    void dismissProgress();

    void createTab(@StringRes int title, boolean selected);
}
