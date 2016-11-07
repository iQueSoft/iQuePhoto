package net.iquesoft.iquephoto.view.activity.interfaces;

import android.graphics.Bitmap;
import android.support.annotation.StringRes;

public interface IShareActivityView {
    void initImageSizes(String small, String medium, String original);

    void share(Bitmap bitmap, String applicationId);

    void showAlert(@StringRes int messageBody, String applicationId);
}
