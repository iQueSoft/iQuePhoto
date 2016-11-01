package net.iquesoft.iquephoto.view;

import android.graphics.Bitmap;
import android.support.annotation.StringRes;

public interface IShareActivityView {
    void initImageSizes(String small, String medium, String original);

    void shareToInstagram(Bitmap bitmap);

    void shareToFacebook(Bitmap bitmap);

    void showAlert(@StringRes int messageBody, String applicationId);
}
