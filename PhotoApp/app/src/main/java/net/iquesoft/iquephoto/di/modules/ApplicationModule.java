package net.iquesoft.iquephoto.di.modules;

import android.app.Application;

import net.iquesoft.iquephoto.PhotoApplication;
import net.iquesoft.iquephoto.view.fragment.ToolsFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private PhotoApplication mApplication;

    public ApplicationModule(PhotoApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    ToolsFragment provideToolsFragment() {
        return new ToolsFragment();
    }
}
