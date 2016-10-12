package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import com.isseiaoki.simplecropview.CropImageView;

interface IPreviewActivityPresenter {
    void cropImage(Uri uri, CropImageView cropImageView);

    void flipImageHorizontal(Bitmap bitmap);

    void flipImageVertical(Bitmap bitmap);
}
