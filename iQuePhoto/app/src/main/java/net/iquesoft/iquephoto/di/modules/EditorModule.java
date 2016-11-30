package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.mvp.views.activity.EditorView;

import dagger.Module;
import dagger.Provides;

@Module
public class EditorModule {

    private EditorView mView;

    public EditorModule(EditorView view) {
        mView = view;
    }

    @Provides
    EditorView provideView() {
        return mView;
    }
}
