package net.iquesoft.iquephoto;

import android.app.Application;
import android.content.Context;

import net.iquesoft.iquephoto.di.components.DaggerIApplicationComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.modules.ApplicationModule;

// FIXME: Fix all lint issues.
// TODO: Refactor all code.
// TODO: Change application structure like as https://habrahabr.ru/company/redmadrobot/blog/305798/.
public class PhotoApplication extends Application {

    private IApplicationComponent mComponent;

    public static PhotoApplication get(Context context) {
        return (PhotoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerIApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mComponent.inject(this);
    }

    public IApplicationComponent getApplicationComponent() {
        return mComponent;
    }
}
