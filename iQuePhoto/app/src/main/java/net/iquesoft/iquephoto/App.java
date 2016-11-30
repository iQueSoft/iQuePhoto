package net.iquesoft.iquephoto;

import android.app.Application;

import net.iquesoft.iquephoto.di.components.AppComponent;
import net.iquesoft.iquephoto.di.components.DaggerAppComponent;
import net.iquesoft.iquephoto.di.modules.AppModule;

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
