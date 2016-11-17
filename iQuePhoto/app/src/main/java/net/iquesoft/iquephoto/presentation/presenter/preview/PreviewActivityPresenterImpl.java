package net.iquesoft.iquephoto.presentation.presenter.preview;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.presentation.view.preview.PreviewView;

import javax.inject.Inject;

public class PreviewActivityPresenterImpl implements IPreviewActivityPresenter {

    private static final int[] CROP_MODES = {
            R.string.crop_free,
            R.string.crop_original,
            R.string.crop_square,
            R.string.crop_3_4,
            R.string.crop_4_3,
            R.string.crop_9_16,
            R.string.crop_16_9
    };

    private PreviewView mView;

    @Inject
    public PreviewActivityPresenterImpl(PreviewView view) {
        mView = view;
    }

    private CropCallback cropCallback = new CropCallback() {
        @Override
        public void onSuccess(Bitmap cropped) {

        }

        @Override
        public void onError() {

        }
    };

    private SaveCallback saveCallback = new SaveCallback() {
        @Override
        public void onSuccess(Uri outputUri) {
            mView.startEditingImage(outputUri);
            mView.dismissProgress();
        }

        @Override
        public void onError() {

        }
    };

    @Override
    public void initCropModes() {
        for (int i = 0; i < CROP_MODES.length; i++) {
            if (i == 0) mView.createTab(CROP_MODES[i], true);
            else mView.createTab(CROP_MODES[i], false);
        }
    }

    @Override
    public void cropImage(Uri uri, CropImageView cropImageView) {
        mView.showProgress();
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
            mView.flipImage(drawable);
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
            mView.flipImage(drawable);
        }
    }
}
