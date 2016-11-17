package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.gallery.GalleryActivityPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.gallery.GalleryView;

import dagger.Module;
import dagger.Provides;

@Module
public class GalleryActivityModule {

    private GalleryView mView;

    public GalleryActivityModule(GalleryView view) {
        mView = view;
    }

    @Provides
    GalleryView provideGalleryActivityView() {
        return mView;
    }

    @Provides
    GalleryActivityPresenterImpl provideGalleryActivityPresenterImpl(GalleryView view) {
        return new GalleryActivityPresenterImpl(mView);
    }
}
