package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.StartActivityPresenterImpl;
import net.iquesoft.iquephoto.view.IStartActivityView;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link net.iquesoft.iquephoto.presenter.StartActivityPresenter}.
 */
@Module
public class StartActivityModule {

    private IStartActivityView view;

    public StartActivityModule(IStartActivityView view) {
        this.view = view;
    }

    @Provides
    public StartActivityPresenterImpl provideMainActivityPresenterImpl(IStartActivityView view) {
        return new StartActivityPresenterImpl(view);
    }

    @Provides
    public IStartActivityView provideStartActivityContractView() {
        return view;
    }
}
