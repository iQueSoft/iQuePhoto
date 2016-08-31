package net.iquesoft.iquephoto.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.iquesoft.iquephoto.PhotoApplication;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(PhotoApplication.get(this).getApplicationComponent());
    }

    protected abstract void setupComponent(IApplicationComponent component);
}
