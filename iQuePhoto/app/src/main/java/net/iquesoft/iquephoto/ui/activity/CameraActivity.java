package net.iquesoft.iquephoto.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.HasComponent;
import net.iquesoft.iquephoto.di.components.DaggerICameraActivityComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.ICameraActivityComponent;
import net.iquesoft.iquephoto.di.modules.CameraActivityModule;
import net.iquesoft.iquephoto.presentation.presenter.activity.CameraPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.CameraActivityView;
import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends BaseActivity implements CameraActivityView, HasComponent<ICameraActivityComponent> {

    @Inject
    CameraPresenterImpl presenter;

    @Inject
    CameraFragment cameraFragment;

    @Inject
    Camera2Fragment camera2Fragment;

    @Inject
    CameraFiltersFragment cameraFiltersFragment;

    private FragmentManager mFragmentManager;
    private ICameraActivityComponent mComponent;

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
        presenter.initializeCamera(cameraFragment, camera2Fragment);
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        mComponent = DaggerICameraActivityComponent.builder()
                .iApplicationComponent(component)
                .cameraActivityModule(new CameraActivityModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    public ICameraActivityComponent getComponent() {
        return mComponent;
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

    @OnClick(R.id.cameraCaptureImageButton)
    void onClickCapture() {
        // TODO: Camera capture button.
    }

    @OnClick(R.id.cameraCloseImageButton)
    void onClickCloseButton() {
        finish();
    }

    @OnClick(R.id.cameraFiltersImageButton)
    void onClickFiltersButton() {
        presenter.showFilters(cameraFiltersFragment);
    }
}
