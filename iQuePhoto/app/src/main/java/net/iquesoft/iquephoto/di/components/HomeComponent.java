package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.HomeModule;
import net.iquesoft.iquephoto.ui.activities.HomeActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = HomeModule.class)

public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
