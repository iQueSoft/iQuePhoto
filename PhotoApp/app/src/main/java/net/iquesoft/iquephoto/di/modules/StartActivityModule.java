package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.activity.StartActivityPresenterImpl;
import net.iquesoft.iquephoto.view.activity.interfaces.IStartActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class StartActivityModule {

    private IStartActivityView mView;

    public StartActivityModule(IStartActivityView view) {
        mView = view;
    }

    @Provides
    StartActivityPresenterImpl provideMainActivityPresenterImpl(IStartActivityView view) {
        return new StartActivityPresenterImpl(view);
    }

    @Provides
    IStartActivityView provideStartActivityContractView() {
        return mView;
    }
}
