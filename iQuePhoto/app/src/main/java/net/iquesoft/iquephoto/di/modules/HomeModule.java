package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.mvp.views.activity.HomeView;

import dagger.Module;

@Module
public class HomeModule {

    private HomeView mView;

    public HomeModule(HomeView view) {
        mView = view;
    }

}
