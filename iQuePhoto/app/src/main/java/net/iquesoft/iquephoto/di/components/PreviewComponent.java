package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.PreviewActivityModule;
import net.iquesoft.iquephoto.ui.activity.PreviewActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = PreviewActivityModule.class)
public interface PreviewComponent {

    void inject(PreviewActivity previewActivity);
}
