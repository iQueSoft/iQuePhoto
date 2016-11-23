package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.PreviewActivityModule;
import net.iquesoft.iquephoto.ui.activity.PreviewActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = IApplicationComponent.class,
        modules = PreviewActivityModule.class)
public interface IPreviewActivityComponent {

    void inject(PreviewActivity previewActivity);
}
