package net.iquesoft.iquephoto.ui.activity;

import android.os.Bundle;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.HasComponent;
import net.iquesoft.iquephoto.di.components.DaggerICameraActivityComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.ICameraActivityComponent;
import net.iquesoft.iquephoto.di.modules.CameraActivityModule;
import net.iquesoft.iquephoto.presentation.view.activity.CameraView;
import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;

import javax.inject.Inject;

public class CameraActivity extends BaseActivity implements CameraView, HasComponent<ICameraActivityComponent> {

    @Inject
    Camera2Fragment camera2Fragment;

    private ICameraActivityComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
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
}
