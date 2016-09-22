package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.ShareActivityPresenterImpl;
import net.iquesoft.iquephoto.view.IShareActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class ShareActivityModule {
    private IShareActivityView view;

    public ShareActivityModule(IShareActivityView view) {
        this.view = view;
    }

    @Provides
    public IShareActivityView provideView() {
        return view;
    }

    @Provides
    public ShareActivityPresenterImpl provideShareActivityPresenterImpl() {
        return new ShareActivityPresenterImpl();
    }
}
