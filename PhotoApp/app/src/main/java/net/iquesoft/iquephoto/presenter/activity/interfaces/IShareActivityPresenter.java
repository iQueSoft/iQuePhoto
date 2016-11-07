package net.iquesoft.iquephoto.presenter.activity.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

public interface IShareActivityPresenter {
    void calculateSizesForCompressing(Bitmap bitmap);

    void shareTo(Context context, String applicationId, Bitmap bitmap);
}
