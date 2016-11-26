package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.activity.SharePresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.ShareView;

import dagger.Module;
import dagger.Provides;

@Module
public class ShareModule {

    private ShareView mView;

    public ShareModule(ShareView view) {
        mView = view;
    }

    @Provides
    ShareView provideView() {
        return mView;
    }

    @Provides
    SharePresenterImpl provideShareActivityPresenterImpl() {
        return new SharePresenterImpl(mView);
    }
}
