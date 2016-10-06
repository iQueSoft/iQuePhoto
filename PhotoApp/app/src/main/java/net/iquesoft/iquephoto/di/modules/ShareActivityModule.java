package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.ShareActivityPresenterImpl;
import net.iquesoft.iquephoto.view.IShareActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class ShareActivityModule {

    private IShareActivityView mView;

    public ShareActivityModule(IShareActivityView view) {
        mView = view;
    }

    @Provides
    IShareActivityView provideView() {
        return mView;
    }

    @Provides
    ShareActivityPresenterImpl provideShareActivityPresenterImpl() {
        return new ShareActivityPresenterImpl(mView);
    }
}
