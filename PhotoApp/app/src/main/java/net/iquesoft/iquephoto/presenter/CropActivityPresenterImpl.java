package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import net.iquesoft.iquephoto.view.ICropActivityView;

import java.io.File;

import javax.inject.Inject;

public class CropActivityPresenterImpl implements ICropActivityPresenter {

    private ICropActivityView view;

    @Inject
    public CropActivityPresenterImpl(ICropActivityView view) {
        this.view = view;
    }

    private CropCallback cropCallback = new CropCallback() {
        @Override
        public void onSuccess(Bitmap cropped) {
            view.dismissProgress();
            //view.startEditingImage(cropped);
        }

        @Override
        public void onError() {

        }
    };

    private SaveCallback saveCallback = new SaveCallback() {
        @Override
        public void onSuccess(Uri outputUri) {
            view.dismissProgress();
            view.startEditingImage(outputUri);
        }

        @Override
        public void onError() {

        }
    };

    @Override
    public void cropImage(Uri uri, CropImageView cropImageView) {
        view.showProgress();
        cropImageView.startCrop(uri, cropCallback, saveCallback);
    }
}
