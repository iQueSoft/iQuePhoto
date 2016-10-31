package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.PhotoApplication;
import net.iquesoft.iquephoto.di.modules.ApplicationModule;
import net.iquesoft.iquephoto.view.fragment.ToolsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class}
)
public interface IApplicationComponent {
    void inject(PhotoApplication application);

    ToolsFragment toolsFragment();
}
