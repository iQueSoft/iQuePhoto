package net.iquesoft.iquephoto.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.isseiaoki.simplecropview.CropImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.DaggerICropActivityComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.ICropActivityComponent;
import net.iquesoft.iquephoto.di.modules.CropActivityModule;
import net.iquesoft.iquephoto.presentation.presenter.activity.PreviewActivityPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.PreviewView;
import net.iquesoft.iquephoto.util.BitmapUtil;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: Replace crop to EditorActivity like as tool.
public class PreviewActivity extends BaseActivity implements PreviewView, IHasComponent<ICropActivityComponent> {

    private Bitmap mBitmap;

    private MaterialDialog mProgressDialog;

    @BindView(R.id.cropTabLayout)
    TabLayout tabLayout;

    @BindView(R.id.cropImageView)
    CropImageView cropImageView;

    private ICropActivityComponent mComponent;

    @Inject
    PreviewActivityPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_preview);

        mBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("Image"));

        BitmapUtil.logBitmapInfo("Preview", mBitmap);

        ButterKnife.bind(this);

        presenter.initCropModes();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        cropImageView.setCropMode(CropImageView.CropMode.FREE);
                        break;
                    case 1:
                        cropImageView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
                        break;
                    case 2:
                        cropImageView.setCropMode(CropImageView.CropMode.SQUARE);
                        break;
                    case 3:
                        cropImageView.setCropMode(CropImageView.CropMode.RATIO_3_4);
                        break;
                    case 4:
                        cropImageView.setCropMode(CropImageView.CropMode.RATIO_4_3);
                        break;
                    case 5:
                        cropImageView.setCropMode(CropImageView.CropMode.RATIO_9_16);
                        break;
                    case 6:
                        cropImageView.setCropMode(CropImageView.CropMode.RATIO_16_9);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        cropImageView.setImageBitmap(mBitmap);
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        mComponent = DaggerICropActivityComponent.builder()
                .iApplicationComponent(component)
                .cropActivityModule(new CropActivityModule(this))
                .build();
        mComponent.inject(this);
    }

    // FIXME: Flip bug.
    @OnClick(R.id.buttonFlipHorizontal)
    void onClickFlipHorizontal() {
        presenter.flipImageHorizontal(cropImageView.getImageBitmap());
    }

    @OnClick(R.id.buttonFlipVertical)
    void onClickFlipVertical() {
        presenter.flipImageVertical(cropImageView.getImageBitmap());
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

    public ICropActivityComponent getComponent() {
        return mComponent;
    }

    @Override
    public void startEditingImage(Uri uri) {
        Intent intent = new Intent(PreviewActivity.this, EditorActivity.class);
        intent.setData(uri);
        startActivity(intent);
        mProgressDialog.dismiss();
        finish();
    }

    @Override
    public void showProgress() {
        mProgressDialog = new MaterialDialog.Builder(this)
                .content(R.string.processing)
                .progress(true, 0)
                .widgetColor(Color.BLACK)
                .contentColor(Color.BLACK)
                .canceledOnTouchOutside(false)
                .show();
    }

    @Override
    public void flipImage(Drawable drawable) {
        cropImageView.setImageDrawable(drawable);
    }

    @Override
    public void dismissProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void createTab(@StringRes int title, boolean selected) {
        tabLayout.addTab(tabLayout.newTab().setText(title), selected);
    }
}
