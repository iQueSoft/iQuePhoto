package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.CropActivityPresenterImpl;
import net.iquesoft.iquephoto.view.ICropActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class CropActivityModule {

    private ICropActivityView view;

    public CropActivityModule(ICropActivityView view) {
        this.view = view;
    }

    @Provides
    ICropActivityView provideView() {
        return view;
    }

    @Provides
    CropActivityPresenterImpl provideCropActivityPresenterImpl() {
        return new CropActivityPresenterImpl();
    }
}
