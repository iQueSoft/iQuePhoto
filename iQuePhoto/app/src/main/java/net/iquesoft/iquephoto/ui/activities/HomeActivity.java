package net.iquesoft.iquephoto.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.presentation.presenters.activity.HomeActivityPresenter;
import net.iquesoft.iquephoto.presentation.views.activity.HomeView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends MvpAppCompatActivity implements HomeView {
    public static final int REQ_CAMERA = 1;

    @InjectPresenter
    HomeActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_camera)
    void onClickCamera() {
        mPresenter.openCamera(this);
    }

    @OnClick(R.id.gallery_button)
    void onClickGallery() {
        mPresenter.openGallery(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.startEditing(requestCode, resultCode);
    }

    @Override
    public void startGallery() {
        Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    @Override
    public void startCamera(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQ_CAMERA);
        }
    }

    @Override
    public void startEditing(String photoPath) {
        Intent intent = new Intent(HomeActivity.this, PreviewActivity.class);
        intent.putExtra(PreviewActivity.IMAGE_PATH, photoPath);
        startActivity(intent);
    }

    @Override
    public void showPermissionDenied(@StringRes int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(getString(R.string.permission_denied))
                .setMessage(getString(message))
                .setPositiveButton(getString(R.string.go_to_app_settings), (dialogInterface, i) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + "net.iquesoft.iquephoto"));
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(getString(R.string.dismiss), (dialogInterface, i1) -> dialogInterface.dismiss());
        builder.show();
    }
}