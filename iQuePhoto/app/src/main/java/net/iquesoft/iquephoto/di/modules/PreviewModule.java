package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.activity.PreviewPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.PreviewView;

import dagger.Module;
import dagger.Provides;

@Module
public class PreviewModule {

    private PreviewView mView;

    public PreviewModule(PreviewView view) {
        mView = view;
    }

    @Provides
    PreviewView provideView() {
        return mView;
    }

    @Provides
    PreviewPresenterImpl providePreviewActivityPresenterImpl() {
        return new PreviewPresenterImpl(mView);
    }
}
