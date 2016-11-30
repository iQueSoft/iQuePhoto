package net.iquesoft.iquephoto.mvp.views.activity;

import android.graphics.Bitmap;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

public interface ShareView extends MvpView {
    void initImageSizes(String small, String medium, String original);

    void share(Bitmap bitmap, String applicationId);

    void showAlert(@StringRes int messageBody, String applicationId);
}
