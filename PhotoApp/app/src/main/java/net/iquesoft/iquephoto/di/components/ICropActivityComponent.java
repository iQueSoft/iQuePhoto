package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.CropActivityModule;
import net.iquesoft.iquephoto.view.activity.PreviewActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = IApplicationComponent.class,
        modules = CropActivityModule.class)
public interface ICropActivityComponent {

    void inject(PreviewActivity previewActivity);
}
