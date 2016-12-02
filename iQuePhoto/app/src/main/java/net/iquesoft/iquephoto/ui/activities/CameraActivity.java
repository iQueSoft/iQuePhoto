package net.iquesoft.iquephoto.ui.activities;

import android.graphics.ColorMatrix;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.di.components.CameraComponent;
import net.iquesoft.iquephoto.mvp.common.BaseActivity;
import net.iquesoft.iquephoto.mvp.presenters.activity.CameraPresenter;
import net.iquesoft.iquephoto.mvp.presenters.fragment.Camera2Presenter;
import net.iquesoft.iquephoto.mvp.views.activity.CameraActivityView;
import net.iquesoft.iquephoto.ui.fragments.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragments.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragments.CameraFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends BaseActivity implements CameraActivityView {
    @InjectPresenter
    CameraPresenter presenter;

    /*@InjectPresenter
    Camera2Presenter camera2Presenter;*/

    /*@Inject
    CameraFragment cameraFragment;

    @Inject
    Camera2Fragment camera2Fragment;

    @Inject
    CameraFiltersFragment cameraFiltersFragment;*/

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);

        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initializeCamera(new CameraFragment(), new Camera2Fragment());
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // TODO: camera2Fragment.setFilter(colorMatrix);
        }
    }

    @OnClick(R.id.cameraCaptureImageButton)
    void onClickCapture() {
        // TODO: Camera capture button.
        //camera2Presenter.takePhoto();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // TODO: camera2Fragment.takePhoto();
        } else {
            // TODO: Camera capture for camera 1.
        }
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
