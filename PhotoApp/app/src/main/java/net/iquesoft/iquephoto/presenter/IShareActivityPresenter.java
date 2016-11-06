package net.iquesoft.iquephoto.presenter;

import android.content.Context;
import android.graphics.Bitmap;

interface IShareActivityPresenter {
    void calculateSizesForCompressing(Bitmap bitmap);

    void shareTo(Context context, String applicationId, Bitmap bitmap);
}
