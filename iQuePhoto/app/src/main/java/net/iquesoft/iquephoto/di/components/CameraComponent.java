package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.CameraModule;
import net.iquesoft.iquephoto.ui.activities.CameraActivity;
import net.iquesoft.iquephoto.ui.fragments.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragments.CameraFiltersFragment;
import net.iquesoft.iquephoto.ui.fragments.CameraFragment;

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
