package net.iquesoft.iquephoto.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.tbruyelle.rxpermissions.RxPermissions;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.presentation.views.activity.HomeView;
import net.iquesoft.iquephoto.presentation.presenters.activity.HomePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: Make real time permissions without RxPermission library.
public class HomeActivity extends MvpAppCompatActivity implements HomeView {
    private static final int REQ_CAMERA = 1;

    @InjectPresenter
    HomePresenter presenter;

    @BindView(R.id.applicationName)
    TextView applicationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        calculateMaxBitmapSize(this);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.cameraButton)
    void onClickCamera() {
        presenter.openCamera();
    }

    @OnClick(R.id.galleryButton)
    void onClickGallery() {
        presenter.openGallery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(HomeActivity.this, PreviewActivity.class);

        switch (requestCode) {
            case REQ_CAMERA:
                startActivity(intent);
                break;
        }
    }

    @Override
    public void startGallery() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertMaterialDialog)
                                .setTitle(getString(R.string.permission_denied))
                                .setMessage(getString(R.string.read_storage_permission))
                                .setPositiveButton(getString(R.string.go_to_app_settings), (dialogInterface, i) -> {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + "net.iquesoft.iquephoto"));
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                })
                                .setNegativeButton(getString(R.string.ok), (dialogInterface, i1) -> {
                                    dialogInterface.dismiss();
                                });
                        builder.show();
                    }
                });
    }

    @Override
    public void startCamera() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertMaterialDialog)
                                .setTitle(getString(R.string.permission_denied))
                                .setMessage(getString(R.string.camera_storage_permission))
                                .setPositiveButton(getString(R.string.go_to_app_settings), (dialogInterface, i) -> {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + "net.iquesoft.iquephoto"));
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                })
                                .setNegativeButton(getString(R.string.ok), (dialogInterface, i1) -> {
                                    dialogInterface.dismiss();
                                });
                        builder.show();
                    }
                });
    }

    public static int calculateMaxBitmapSize(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        int width, height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();
            height = display.getHeight();
        }

        // Twice the device screen diagonal as default
        int maxBitmapSize = (int) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

        // Check for max texture size via Canvas
        Canvas canvas = new Canvas();
        final int maxCanvasSize = Math.min(canvas.getMaximumBitmapWidth(), canvas.getMaximumBitmapHeight());
        if (maxCanvasSize > 0) {
            maxBitmapSize = Math.min(maxBitmapSize, maxCanvasSize);
        }

        /*// Check for max texture size via GL
        final int maxTextureSize = EglUtils.getMaxTextureSize();
        if (maxTextureSize > 0) {
            maxBitmapSize = Math.min(maxBitmapSize, maxTextureSize);
        }*/

        Log.d("MAX BITMAP", "maxBitmapSize: " + maxBitmapSize);
        return maxBitmapSize;
    }
}
