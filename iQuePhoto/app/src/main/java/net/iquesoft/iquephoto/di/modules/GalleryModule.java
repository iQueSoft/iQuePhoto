package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.mvp.views.activity.GalleryView;

import dagger.Module;
import dagger.Provides;

@Module
public class GalleryModule {

    private GalleryView mView;

    public GalleryModule(GalleryView view) {
        mView = view;
    }

    @Provides
    GalleryView provideGalleryActivityView() {
        return mView;
    }
}
