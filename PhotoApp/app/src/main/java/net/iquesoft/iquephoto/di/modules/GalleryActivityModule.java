package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.activity.GalleryActivityPresenterImpl;
import net.iquesoft.iquephoto.view.activity.interfaces.IGalleryActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class GalleryActivityModule {

    private IGalleryActivityView mView;

    public GalleryActivityModule(IGalleryActivityView view) {
        mView = view;
    }

    @Provides
    IGalleryActivityView provideGalleryActivityView() {
        return mView;
    }

    @Provides
    GalleryActivityPresenterImpl provideGalleryActivityPresenterImpl(IGalleryActivityView view) {
        return new GalleryActivityPresenterImpl(mView);
    }
}
