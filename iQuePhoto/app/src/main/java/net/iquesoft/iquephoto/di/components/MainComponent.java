package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.MainModule;
import net.iquesoft.iquephoto.ui.activity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = MainModule.class)

public interface MainComponent {
    void inject(MainActivity mainActivity);
}
