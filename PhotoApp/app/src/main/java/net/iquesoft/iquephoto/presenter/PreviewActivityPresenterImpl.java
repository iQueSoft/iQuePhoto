package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import net.iquesoft.iquephoto.view.IPreviewActivityView;

import javax.inject.Inject;

public class PreviewActivityPresenterImpl implements IPreviewActivityPresenter {

    private IPreviewActivityView view;

    @Inject
    public PreviewActivityPresenterImpl(IPreviewActivityView view) {
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

    @Override
    public void flipImageHorizontal(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        Bitmap flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if (flippedBitmap != null) {
            flippedBitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            Drawable drawable = new BitmapDrawable(flippedBitmap);
            view.flipImage(drawable);
        }
    }

    @Override
    public void flipImageVertical(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if (flippedBitmap != null) {
            flippedBitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            Drawable drawable = new BitmapDrawable(flippedBitmap);
            view.flipImage(drawable);
        }
    }
}
