package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.activity.ShareActivityPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.ShareView;

import dagger.Module;
import dagger.Provides;

@Module
public class ShareActivityModule {

    private ShareView mView;

    public ShareActivityModule(ShareView view) {
        mView = view;
    }

    @Provides
    ShareView provideView() {
        return mView;
    }

    @Provides
    ShareActivityPresenterImpl provideShareActivityPresenterImpl() {
        return new ShareActivityPresenterImpl(mView);
    }
}
