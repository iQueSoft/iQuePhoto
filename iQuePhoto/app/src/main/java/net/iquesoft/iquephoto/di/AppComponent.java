package net.iquesoft.iquephoto.di;

import android.content.Context;

import net.iquesoft.iquephoto.di.modules.AppModule;
import net.iquesoft.iquephoto.di.modules.EditorModule;
import net.iquesoft.iquephoto.models.Filter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.AdjustPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.FiltersPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.FramesPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.OverlaysPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.ToolsPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, EditorModule.class})
public interface AppComponent {
    Context getContext();

    void inject(ToolsPresenter toolsPresenter);

    void inject(AdjustPresenter adjustPresenter);

    void inject(FiltersPresenter filtersPresenter);

    void inject(OverlaysPresenter overlaysPresenter);

    void inject(FramesPresenter framesPresenter);
}
