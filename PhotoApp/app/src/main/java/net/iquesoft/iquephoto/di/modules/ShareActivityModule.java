package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.share.ShareActivityPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.share.ShareView;

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
