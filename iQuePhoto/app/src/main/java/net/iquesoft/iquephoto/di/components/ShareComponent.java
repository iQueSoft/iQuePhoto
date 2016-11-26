package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.ShareModule;
import net.iquesoft.iquephoto.ui.activity.ShareActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = ShareModule.class)

public interface ShareComponent {
    void inject(ShareActivity shareActivity);
}
