package net.iquesoft.iquephoto.presentation.presenter.activity.interfaces;

import android.graphics.Bitmap;
import android.net.Uri;

import com.isseiaoki.simplecropview.CropImageView;

public interface PreviewPresenter {
    void initCropModes();

    void cropImage(Uri uri, CropImageView cropImageView);

    void flipImageHorizontal(Bitmap bitmap);

    void flipImageVertical(Bitmap bitmap);
}
