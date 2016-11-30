package net.iquesoft.iquephoto.mvp.presenters.activity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.mvp.views.activity.PreviewView;

import javax.inject.Inject;

@InjectViewState
public class PreviewPresenter extends MvpPresenter<PreviewView> {

    private static final int[] CROP_MODES = {
            R.string.crop_free,
            R.string.crop_original,
            R.string.crop_square,
            R.string.crop_3_4,
            R.string.crop_4_3,
            R.string.crop_9_16,
            R.string.crop_16_9
    };

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
            getViewState().startEditingImage(outputUri);
            getViewState().dismissProgress();
        }

        @Override
        public void onError() {

        }
    };

    public void initCropModes() {
        for (int i = 0; i < CROP_MODES.length; i++) {
            if (i == 0) getViewState().createTab(CROP_MODES[i], true);
            else getViewState().createTab(CROP_MODES[i], false);
        }
    }

    public void cropImage(Uri uri, CropImageView cropImageView) {
        getViewState().showProgress();
        cropImageView.startCrop(uri, cropCallback, saveCallback);
    }

    public void flipImageHorizontal(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        Bitmap flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if (flippedBitmap != null) {
            flippedBitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            Drawable drawable = new BitmapDrawable(flippedBitmap);
            getViewState().flipImage(drawable);
        }
    }

    public void flipImageVertical(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if (flippedBitmap != null) {
            flippedBitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            Drawable drawable = new BitmapDrawable(flippedBitmap);
            getViewState().flipImage(drawable);
        }
    }
}
