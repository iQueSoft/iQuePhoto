package net.iquesoft.iquephoto.ui.activities;

import android.graphics.ColorMatrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.camera.CameraModule;
import net.iquesoft.iquephoto.core.camera.CameraView;
import net.iquesoft.iquephoto.mvp.presenters.activity.CameraPresenter;
import net.iquesoft.iquephoto.mvp.views.activity.CameraActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends MvpAppCompatActivity implements CameraActivityView {
    @InjectPresenter
    CameraPresenter presenter;

    @BindView(R.id.cameraView)
    CameraView mCameraView;

    private CameraModule mCameraModule;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);

        ButterKnife.bind(this);

        mCameraModule = new CameraModule(this, mCameraView);

        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraModule.startHandlerThread();

        mCameraView.setCameraModule(mCameraModule);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraModule.closeCamera();
        mCameraModule.stopHandlerThread();
    }

    @Override
    public void setupCamera(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.cameraFrameLayout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void setupFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.cameraFragmentFrameLayout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void hideFiltersButton() {
        super.onBackPressed();
    }

    @Override
    public void setFilter(ColorMatrix colorMatrix) {
    }

    @OnClick(R.id.cameraCaptureImageButton)
    void onClickCapture() {
        // TODO: camera2Fragment.takePhoto();
    }

    @OnClick(R.id.cameraCloseImageButton)
    void onClickCloseButton() {
        finish();
    }

    @OnClick(R.id.cameraFiltersImageButton)
    void onClickFiltersButton() {
        // TODO: presenter.showFilters(cameraFiltersFragment);
    }
}
