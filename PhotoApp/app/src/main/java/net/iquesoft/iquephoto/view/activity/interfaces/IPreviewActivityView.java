package net.iquesoft.iquephoto.view.activity.interfaces;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.StringRes;

public interface IPreviewActivityView {

    void startEditingImage(Uri uri);

    void showProgress();

    void flipImage(Drawable drawable);

    void dismissProgress();

    void createTab(@StringRes int title, boolean selected);
}
