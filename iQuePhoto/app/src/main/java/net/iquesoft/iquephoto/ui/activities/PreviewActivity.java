package net.iquesoft.iquephoto.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.isseiaoki.simplecropview.CropImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.presentation.presenters.activity.PreviewActivityPresenter;
import net.iquesoft.iquephoto.presentation.views.activity.PreviewView;
import net.iquesoft.iquephoto.ui.dialogs.LoadingDialog;
import net.iquesoft.iquephoto.utils.LogHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewActivity extends MvpAppCompatActivity implements PreviewView {
    public static final String IMAGE_PATH = "image path";

    @InjectPresenter
    PreviewActivityPresenter mPresenter;

    @ProvidePresenter
    PreviewActivityPresenter providePreviewPresenter() {
        return new PreviewActivityPresenter(this, getIntent());
    }

    @BindView(R.id.toolbar_preview)
    Toolbar mToolbar;

    @BindView(R.id.tab_layout_crop)
    TabLayout mTabLayout;

    @BindView(R.id.crop_image_view)
    CropImageView mCropImageView;

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoadingDialog = new LoadingDialog(this);

        mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPresenter.changeCropMode(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crop, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_crop:
                mPresenter.cropImage(createSaveUri(), mCropImageView);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.button_flip_horizontal)
    void onClickFlipHorizontal() {
        mPresenter.flipImageHorizontal(mCropImageView.getImageBitmap());
    }

    @OnClick(R.id.button_flip_vertical)
    void onClickFlipVertical() {
        mPresenter.flipImageVertical(mCropImageView.getImageBitmap());
    }

    @OnClick(R.id.button_rotate_left)
    void onClickRotateLeft() {
        mCropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
    }

    @OnClick(R.id.button_rotate_right)
    void onClickRotateRight() {
        mCropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
    }

    public Uri createSaveUri() {
        return Uri.fromFile(new File(getCacheDir(), "cropped"));
    }

    @Override
    public void setupImage(Bitmap bitmap) {
        mCropImageView.setImageBitmap(bitmap);

        LogHelper.logBitmap("Preview Bitmap", bitmap);
    }

    @Override
    public void onCropModeChanged(CropImageView.CropMode cropMode) {
        mCropImageView.setCropMode(cropMode);
    }

    @Override
    public void startEditingImage(Uri uri) {
        Intent intent = new Intent(PreviewActivity.this, EditorActivity.class);
        intent.setData(uri);
        startActivity(intent);
        mLoadingDialog.dismiss();
        finish();
    }

    @Override
    public void showProgress() {
        mLoadingDialog.show();
    }

    @Override
    public void flipImage(Drawable drawable) {
        mCropImageView.setImageDrawable(drawable);
    }

    @Override
    public void dismissProgress() {
        //mProgressDialog.dismiss();
    }

    @Override
    public void createTab(@StringRes int title, boolean selected) {
        mTabLayout.addTab(mTabLayout.newTab().setText(title), selected);
    }
}