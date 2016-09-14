package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.GalleryImagesPresenterImpl;
import net.iquesoft.iquephoto.presenter.IGalleryImagesFragmentPresenter;
import net.iquesoft.iquephoto.presenter.StartActivityPresenterImpl;
import net.iquesoft.iquephoto.view.IStartActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class StartActivityModule {

    private IStartActivityView view;

    public StartActivityModule(IStartActivityView view) {
        this.view = view;
    }

    @Provides
    public StartActivityPresenterImpl provideMainActivityPresenterImpl(IStartActivityView view) {
        return new StartActivityPresenterImpl(view);
    }

    @Provides
    public IStartActivityView provideStartActivityContractView() {
        return view;
    }

    @Provides
    public GalleryImagesPresenterImpl provideGalleryImagesPresenterImpl() {
        return new GalleryImagesPresenterImpl();
    }
}
