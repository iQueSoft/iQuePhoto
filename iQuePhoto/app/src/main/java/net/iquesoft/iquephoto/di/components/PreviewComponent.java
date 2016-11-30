package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.PreviewModule;
import net.iquesoft.iquephoto.ui.activities.PreviewActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = PreviewModule.class)
public interface PreviewComponent {

    void inject(PreviewActivity previewActivity);
}
