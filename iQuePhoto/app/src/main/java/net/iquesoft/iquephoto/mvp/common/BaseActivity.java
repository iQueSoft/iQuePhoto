package net.iquesoft.iquephoto.mvp.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.di.components.AppComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(App.getAppComponent());
    }

    protected abstract void setupComponent(AppComponent component);
}