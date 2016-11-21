package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.activity.GalleryPresenterImpl;
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
    GalleryPresenterImpl provideGalleryActivityPresenterImpl() {
        return new GalleryPresenterImpl(mView);
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
