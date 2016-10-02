package net.iquesoft.iquephoto.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.isseiaoki.simplecropview.CropImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.DaggerICropActivityComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.ICropActivityComponent;
import net.iquesoft.iquephoto.di.modules.CropActivityModule;
import net.iquesoft.iquephoto.presenter.CropActivityPresenterImpl;
import net.iquesoft.iquephoto.view.ICropActivityView;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CropActivity extends BaseActivity implements ICropActivityView, IHasComponent<ICropActivityComponent> {

    private Bitmap bitmap;

    @BindView(R.id.cropImageView)
    CropImageView cropImageView;

    @BindView(R.id.cropProgress)
    ProgressBar progressBar;

    private ICropActivityComponent cropActivityComponent;

    @Inject
    CropActivityPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("bitmap_path"));

        ButterKnife.bind(this);

        cropImageView.setImageBitmap(bitmap);
        cropImageView.setCropMode(CropImageView.CropMode.FREE);
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        cropActivityComponent = DaggerICropActivityComponent.builder()
                .iApplicationComponent(component)
                .cropActivityModule(new CropActivityModule(this))
                .build();
        cropActivityComponent.inject(this);
    }

    @OnClick(R.id.cropFree)
    void onClickCropFree() {
        cropImageView.setCropMode(CropImageView.CropMode.FREE);
    }

    @OnClick(R.id.cropOriginal)
    void onClickCropOriginal() {
        cropImageView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
    }

    @OnClick(R.id.cropSquare)
    void onClickCropSquare() {
        cropImageView.setCropMode(CropImageView.CropMode.SQUARE);
    }

    @OnClick(R.id.crop3x4)
    void onClickCrop3x4() {
        cropImageView.setCropMode(CropImageView.CropMode.RATIO_3_4);
    }

    @OnClick(R.id.crop4x3)
    void onClickCrop4x3() {
        cropImageView.setCropMode(CropImageView.CropMode.RATIO_4_3);
    }

    @OnClick(R.id.crop9x16)
    void onClickCrop9x16() {
        cropImageView.setCropMode(CropImageView.CropMode.RATIO_9_16);
    }

    @OnClick(R.id.crop16x9)
    void onClickCrop16x9() {
        cropImageView.setCropMode(CropImageView.CropMode.RATIO_16_9);
    }

    @OnClick(R.id.buttonCropBack)
    void onClickBack() {
        super.onBackPressed();
    }

    @OnClick(R.id.buttonRotateLeft)
    void onClickRotateLeft() {
        cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
    }

    @OnClick(R.id.buttonRotateRight)
    void onClickRotateRight() {
        cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
    }

    @OnClick(R.id.buttonCrop)
    void onClickCrop() {
        presenter.cropImage(createSaveUri(), cropImageView);
    }

    public Uri createSaveUri() {
        return Uri.fromFile(new File(getCacheDir(), "cropped"));
    }

    @Override
    public ICropActivityComponent getComponent() {
        return cropActivityComponent;
    }

    @Override
    public void startEditingImage(Uri uri) {
        Intent intent = new Intent(CropActivity.this, EditorActivity.class);
        intent.setData(uri);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
