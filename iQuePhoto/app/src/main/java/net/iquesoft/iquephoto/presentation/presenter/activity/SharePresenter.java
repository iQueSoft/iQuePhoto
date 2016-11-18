package net.iquesoft.iquephoto.presentation.presenter.activity;

import android.content.Context;
import android.graphics.Bitmap;

public interface SharePresenter {
    void calculateSizesForCompressing(Bitmap bitmap);

    void shareTo(Context context, String applicationId, Bitmap bitmap);
}
