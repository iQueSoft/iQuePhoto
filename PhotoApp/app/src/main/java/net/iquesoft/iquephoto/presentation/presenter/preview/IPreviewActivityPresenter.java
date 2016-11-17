package net.iquesoft.iquephoto.presentation.presenter.preview;

import android.graphics.Bitmap;
import android.net.Uri;

import com.isseiaoki.simplecropview.CropImageView;

public interface IPreviewActivityPresenter {
    void initCropModes();

    void cropImage(Uri uri, CropImageView cropImageView);

    void flipImageHorizontal(Bitmap bitmap);

    void flipImageVertical(Bitmap bitmap);
}
