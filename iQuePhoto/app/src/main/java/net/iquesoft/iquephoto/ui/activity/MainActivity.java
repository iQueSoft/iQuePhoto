package net.iquesoft.iquephoto.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.DaggerIMainActivityComponent;
import net.iquesoft.iquephoto.di.components.IMainActivityComponent;
import net.iquesoft.iquephoto.di.modules.MainActivityModule;
import net.iquesoft.iquephoto.presentation.view.activity.MainView;
import net.iquesoft.iquephoto.presentation.presenter.activity.MainActivityPresenterImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: Make real time permissions without RxPermission library.
public class MainActivity extends BaseActivity implements MainView, IHasComponent<IMainActivityComponent> {
    private static final int REQ_CAMERA = 1;

    @Inject
    MainActivityPresenterImpl presenter;

    private IMainActivityComponent mComponent;

    @BindView(R.id.applicationName)
    TextView applicationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

        ButterKnife.bind(this);
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        mComponent = DaggerIMainActivityComponent.builder()
                .iApplicationComponent(component)
                .mainActivityModule(new MainActivityModule(this))
                .build();
        mComponent.inject(this);
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
        Intent intent = new Intent(MainActivity.this, PreviewActivity.class);

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
                        Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
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
        Toast.makeText(getBaseContext(), "Coming soon!", Toast.LENGTH_SHORT).show();
    }

    public IMainActivityComponent getComponent() {
        return mComponent;
    }
}
