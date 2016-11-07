package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.activity.PreviewActivityPresenterImpl;
import net.iquesoft.iquephoto.view.activity.interfaces.IPreviewActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class CropActivityModule {

    private IPreviewActivityView mView;

    public CropActivityModule(IPreviewActivityView view) {
        mView = view;
    }

    @Provides
    IPreviewActivityView provideView() {
        return mView;
    }

    @Provides
    PreviewActivityPresenterImpl provideCropActivityPresenterImpl() {
        return new PreviewActivityPresenterImpl(mView);
    }
}
