package net.iquesoft.iquephoto.view;

import android.net.Uri;

public interface ICropActivityView {

    void startEditingImage(Uri uri);

    void showProgress();

    void dismissProgress();
}
