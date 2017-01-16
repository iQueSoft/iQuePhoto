package net.iquesoft.iquephoto.presentation.presenters.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.presentation.views.activity.PreviewView;

import static com.isseiaoki.simplecropview.CropImageView.CropMode.*;

@InjectViewState
public class PreviewPresenter extends MvpPresenter<PreviewView> {

    public PreviewPresenter(@NonNull Intent intent) {
        Bitmap bitmap = BitmapFactory.decodeFile(intent.getStringExtra("Image"));

        getViewState().setupImage(bitmap);
        initCropModes();
    }

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

    private void initCropModes() {
        for (int i = 0; i < CROP_MODES.length; i++) {
            if (i == 0) getViewState().createTab(CROP_MODES[i], true);
            else getViewState().createTab(CROP_MODES[i], false);
        }
    }

    public void changeCropMode(@NonNull TabLayout.Tab tab) {
        CropImageView.CropMode cropMode = FREE;

        switch (tab.getPosition()) {
            case 0:
                cropMode = FREE;
                break;
            case 1:
                cropMode = FIT_IMAGE;
                break;
            case 2:
                cropMode = SQUARE;
                break;
            case 3:
                cropMode = RATIO_3_4;
                break;
            case 4:
                cropMode = RATIO_4_3;
                break;
            case 5:
                cropMode = RATIO_9_16;
                break;
            case 6:
                cropMode = RATIO_16_9;
                break;
        }

        getViewState().onCropModeChanged(cropMode);
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
