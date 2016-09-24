package net.iquesoft.iquephoto.presenter;

import android.net.Uri;

import com.isseiaoki.simplecropview.CropImageView;

/**
 * Created by Sergey on 9/22/2016.
 */

public interface ICropActivityPresenter {
    void cropImage(Uri uri, CropImageView cropImageView);
}
