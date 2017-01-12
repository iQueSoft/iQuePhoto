package net.iquesoft.iquephoto;

import android.app.Application;

import net.iquesoft.iquephoto.di.AppComponent;
import net.iquesoft.iquephoto.di.DaggerAppComponent;
import net.iquesoft.iquephoto.di.modules.AppModule;

// TODO: Load image in sampled size to works with image most effective.
// https://developer.android.com/training/displaying-bitmaps/load-bitmap.html
public class App extends Application {
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
