package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.views.activity.PreviewView;

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
}
