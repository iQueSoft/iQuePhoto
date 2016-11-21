package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.view.activity.MainView;
import net.iquesoft.iquephoto.presentation.presenter.activity.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private MainView mView;

    public MainActivityModule(MainView view) {
        mView = view;
    }

    @Provides
    MainPresenterImpl provideMainActivityPresenterImpl(MainView view) {
        return new MainPresenterImpl(view);
    }

    @Provides
    MainView provideMainView() {
        return mView;
    }
}
