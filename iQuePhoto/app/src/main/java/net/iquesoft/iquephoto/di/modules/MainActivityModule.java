package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.view.main.MainView;
import net.iquesoft.iquephoto.presentation.presenter.main.MainActivityPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private MainView mView;

    public MainActivityModule(MainView view) {
        mView = view;
    }

    @Provides
    MainActivityPresenterImpl provideMainActivityPresenterImpl(MainView view) {
        return new MainActivityPresenterImpl(view);
    }

    @Provides
    MainView provideMainView() {
        return mView;
    }
}
