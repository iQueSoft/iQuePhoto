package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.GalleryImagesPresenterImpl;
import net.iquesoft.iquephoto.presenter.IGalleryImagesFragmentPresenter;
import net.iquesoft.iquephoto.presenter.StartActivityPresenterImpl;
import net.iquesoft.iquephoto.view.IStartActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class StartActivityModule {

    private IStartActivityView mView;

    public StartActivityModule(IStartActivityView view) {
        mView = view;
    }

    @Provides
    StartActivityPresenterImpl provideMainActivityPresenterImpl(IStartActivityView view) {
        return new StartActivityPresenterImpl(view);
    }

    @Provides
    IStartActivityView provideStartActivityContractView() {
        return mView;
    }

    @Provides
    GalleryImagesPresenterImpl provideGalleryImagesPresenterImpl() {
        return new GalleryImagesPresenterImpl();
    }
}
