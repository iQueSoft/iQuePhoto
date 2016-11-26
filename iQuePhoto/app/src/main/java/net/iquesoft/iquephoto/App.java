package net.iquesoft.iquephoto;

import android.app.Application;
import android.content.Context;

import net.iquesoft.iquephoto.di.components.ApplicationComponent;
import net.iquesoft.iquephoto.di.components.DaggerApplicationComponent;
import net.iquesoft.iquephoto.di.modules.ApplicationModule;

// FIXME: Fix all lint issues.
// TODO: Refactor all code.
public class App extends Application {

    private ApplicationComponent mComponent;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mComponent;
    }
}
