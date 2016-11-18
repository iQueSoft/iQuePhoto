package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.activity.GalleryActivityPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.GalleryAlbumsPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.GalleryImagesPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.GalleryView;

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
    GalleryActivityPresenterImpl provideGalleryActivityPresenterImpl() {
        return new GalleryActivityPresenterImpl(mView);
    }

    @Provides
    GalleryImagesPresenterImpl provideGalleryImagesPresenterImpl() {
        return new GalleryImagesPresenterImpl(mView);
    }

    @Provides
    GalleryAlbumsPresenterImpl provideGalleryAlbumsPresenterImpl() {
        return new GalleryAlbumsPresenterImpl(mView);
    }
}
