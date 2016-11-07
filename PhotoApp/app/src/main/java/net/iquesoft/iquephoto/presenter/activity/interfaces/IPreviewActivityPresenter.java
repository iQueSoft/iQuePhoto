package net.iquesoft.iquephoto.presenter.activity.interfaces;

import android.graphics.Bitmap;
import android.net.Uri;

import com.isseiaoki.simplecropview.CropImageView;

public interface IPreviewActivityPresenter {
    void initCropModes();

    void cropImage(Uri uri, CropImageView cropImageView);

    void flipImageHorizontal(Bitmap bitmap);

    void flipImageVertical(Bitmap bitmap);
}
