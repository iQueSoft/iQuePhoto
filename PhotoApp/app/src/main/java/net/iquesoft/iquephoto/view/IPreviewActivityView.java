package net.iquesoft.iquephoto.view;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public interface IPreviewActivityView {

    void startEditingImage(Uri uri);

    void showProgress();

    void flipImage(Drawable drawable);

    void dismissProgress();
}
