package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.preview.PreviewActivityPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.preview.PreviewView;

import dagger.Module;
import dagger.Provides;

@Module
public class CropActivityModule {

    private PreviewView mView;

    public CropActivityModule(PreviewView view) {
        mView = view;
    }

    @Provides
    PreviewView provideView() {
        return mView;
    }

    @Provides
    PreviewActivityPresenterImpl provideCropActivityPresenterImpl() {
        return new PreviewActivityPresenterImpl(mView);
    }
}
