package net.iquesoft.iquephoto.view.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.DaggerIStartActivityComponent;
import net.iquesoft.iquephoto.di.components.IStartActivityComponent;
import net.iquesoft.iquephoto.di.modules.StartActivityModule;
import net.iquesoft.iquephoto.presenter.StartActivityPresenterImpl;
import net.iquesoft.iquephoto.utils.GalleryImageLoader;
import net.iquesoft.iquephoto.view.IStartActivityView;
import net.iquesoft.iquephoto.view.fragment.GalleryImagesFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends BaseActivity implements IStartActivityView, IHasComponent<IStartActivityComponent> {
    private static final int REQ_CAMERA = 1;
    private static final int REQ_GALLERY = 2;

    @Inject
    StartActivityPresenterImpl presenter;

    private GalleryImagesFragment mGalleryImagesFragment;

    private IStartActivityComponent startActivityComponent;

    @BindView(R.id.applicationName)
    TextView applicationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

        ButterKnife.bind(this);

        applicationTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Blacksword.otf"));

        mGalleryImagesFragment = new GalleryImagesFragment();
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        startActivityComponent = DaggerIStartActivityComponent.builder()
                .iApplicationComponent(component)
                .startActivityModule(new StartActivityModule(this))
                .build();
        startActivityComponent.inject(this);
    }

    @OnClick(R.id.cameraButton)
    void onClickCamera(View view) {
        presenter.openCamera();
    }

    @OnClick(R.id.galleryButton)
    void onClickGallery(View view) {
        presenter.openGallery();
        //mGalleryImagesFragment.show(getSupportFragmentManager(), StartActivity.class.getSimpleName());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(StartActivity.this, PreviewActivity.class);

        switch (requestCode) {
            case REQ_CAMERA:
                startActivity(intent);
                break;
            case REQ_GALLERY:
                try {
                    Uri uri = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri,
                            filePath, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePath[0]);
                    String path = cursor.getString(columnIndex);
                    cursor.close();

                    intent.putExtra("bitmap_path", path);
                    startActivity(intent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void startGallery() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, REQ_GALLERY);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
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

    @Override
    public IStartActivityComponent getComponent() {
        return startActivityComponent;
    }
}
