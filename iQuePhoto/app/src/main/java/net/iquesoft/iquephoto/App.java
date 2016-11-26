package net.iquesoft.iquephoto;

import android.app.Application;
import android.content.Context;

import net.iquesoft.iquephoto.di.components.AppComponent;
import net.iquesoft.iquephoto.di.components.DaggerAppComponent;
import net.iquesoft.iquephoto.di.modules.AppModule;

// FIXME: Fix all lint issues.
// TODO: Refactor all code.
public class App extends Application {

    private AppComponent mComponent;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        mComponent.inject(this);
    }

    public AppComponent getApplicationComponent() {
        return mComponent;
    }
}
