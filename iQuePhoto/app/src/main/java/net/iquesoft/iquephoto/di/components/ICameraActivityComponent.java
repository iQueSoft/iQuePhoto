package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.CameraActivityModule;
import net.iquesoft.iquephoto.ui.activity.CameraActivity;
import net.iquesoft.iquephoto.ui.fragment.Camera2Fragment;
import net.iquesoft.iquephoto.ui.fragment.CameraMainFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = IApplicationComponent.class,
        modules = CameraActivityModule.class)
public interface ICameraActivityComponent {

    void inject(CameraActivity cameraActivity);

    void inject(CameraMainFragment cameraMainFragment);

    void inject(Camera2Fragment camera2Fragment);

    //void inject(FiltersFragment filtersFragment);
}
