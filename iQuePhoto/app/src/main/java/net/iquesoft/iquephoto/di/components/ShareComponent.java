package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.ShareActivityModule;
import net.iquesoft.iquephoto.ui.activity.ShareActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = ShareActivityModule.class)

public interface ShareComponent {
    void inject(ShareActivity shareActivity);
}
