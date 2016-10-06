package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.CropActivityPresenterImpl;
import net.iquesoft.iquephoto.view.ICropActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class CropActivityModule {

    private ICropActivityView mView;

    public CropActivityModule(ICropActivityView view) {
        mView = view;
    }

    @Provides
    ICropActivityView provideView() {
        return mView;
    }

    @Provides
    CropActivityPresenterImpl provideCropActivityPresenterImpl() {
        return new CropActivityPresenterImpl(mView);
    }
}
