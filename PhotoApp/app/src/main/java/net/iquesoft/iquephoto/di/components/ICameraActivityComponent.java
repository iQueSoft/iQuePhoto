package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.CameraActivityModule;
import net.iquesoft.iquephoto.view.activity.CameraActivity;

import dagger.Component;

@ActivityScope
@Component(modules = CameraActivityModule.class)
public interface ICameraActivityComponent {

    void inject(CameraActivity cameraActivity);
}
