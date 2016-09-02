package net.iquesoft.iquephoto;

import android.app.Application;
import android.content.Context;

import net.iquesoft.iquephoto.di.components.DaggerIApplicationComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.modules.ApplicationModule;

public class PhotoApplication extends Application {

    private IApplicationComponent IApplicationComponent;

    public static PhotoApplication get(Context context) {
        return (PhotoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IApplicationComponent = DaggerIApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        IApplicationComponent.inject(this);
    }

    public IApplicationComponent getApplicationComponent() {
        return IApplicationComponent;
    }
}
