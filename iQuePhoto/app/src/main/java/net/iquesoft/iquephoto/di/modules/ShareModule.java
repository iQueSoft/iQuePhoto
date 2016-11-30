package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.mvp.views.activity.ShareView;

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

}
