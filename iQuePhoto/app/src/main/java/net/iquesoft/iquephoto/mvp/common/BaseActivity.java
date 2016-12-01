package net.iquesoft.iquephoto.mvp.common;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatActivity;

import net.iquesoft.iquephoto.App;

public abstract class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
    }
}