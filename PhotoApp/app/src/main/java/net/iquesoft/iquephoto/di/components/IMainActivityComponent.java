package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.MainActivityModule;
import net.iquesoft.iquephoto.ui.activity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = IApplicationComponent.class,
        modules = MainActivityModule.class)

public interface IMainActivityComponent {
    void inject(MainActivity mainActivity);
}
