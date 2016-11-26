package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.MainActivityModule;
import net.iquesoft.iquephoto.ui.activity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = MainActivityModule.class)

public interface MainComponent {
    void inject(MainActivity mainActivity);
}
