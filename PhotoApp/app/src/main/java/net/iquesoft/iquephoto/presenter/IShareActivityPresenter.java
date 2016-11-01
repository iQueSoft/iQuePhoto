package net.iquesoft.iquephoto.presenter;

import android.content.Context;
import android.graphics.Bitmap;

interface IShareActivityPresenter {
    void calculateSizesForCompressing(Bitmap bitmap);

    void shareToInstagram(Context context, Bitmap bitmap);

    void shareToFacebook(Context context, Bitmap bitmap);
}
