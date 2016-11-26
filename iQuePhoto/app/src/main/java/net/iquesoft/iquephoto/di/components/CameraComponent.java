package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.CameraModule;
import net.iquesoft.iquephoto.ui.activity.CameraActivity;
import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.CameraFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = CameraModule.class)
public interface CameraComponent {

    void inject(CameraActivity cameraActivity);

    void inject(CameraFragment cameraFragment);

    void inject(Camera2Fragment camera2Fragment);

    void inject(CameraFiltersFragment cameraFiltersFragment);
}
