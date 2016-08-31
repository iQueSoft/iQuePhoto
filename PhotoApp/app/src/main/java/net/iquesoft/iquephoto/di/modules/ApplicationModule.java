package net.iquesoft.iquephoto.di.modules;

import android.app.Application;
import android.content.Context;

import net.iquesoft.iquephoto.PhotoApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final PhotoApplication application;

    public ApplicationModule(PhotoApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }
}
