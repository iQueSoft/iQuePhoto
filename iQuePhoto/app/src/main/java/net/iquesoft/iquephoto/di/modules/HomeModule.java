package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.activity.HomePresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.HomeView;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private HomeView mView;

    public HomeModule(HomeView view) {
        mView = view;
    }

    @Provides
    HomePresenterImpl provideMainActivityPresenterImpl(HomeView view) {
        return new HomePresenterImpl(view);
    }

    @Provides
    HomeView provideMainView() {
        return mView;
    }
}
