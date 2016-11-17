package net.iquesoft.iquephoto.di.modules;

import android.app.Application;

import net.iquesoft.iquephoto.PhotoApplication;
import net.iquesoft.iquephoto.ui.fragment.ToolsFragment;

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
