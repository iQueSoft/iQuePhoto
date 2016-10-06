package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.AdjustFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.DrawingFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.EditorActivityPresenterImpl;
import net.iquesoft.iquephoto.presenter.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.ShowStickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.StickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IEditorActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class EditorActivityModule {

    private IEditorActivityView mView;

    public EditorActivityModule(IEditorActivityView view) {
        mView = view;
    }

    @Provides
    EditorActivityPresenterImpl provideEditorActivityPresenterImpl(IEditorActivityView view) {
        return new EditorActivityPresenterImpl(view);
    }

    @Provides
    IEditorActivityView provideView() {
        return mView;
    }

    @Provides
    FiltersFragmentPresenterImpl provideFiltersFragmentPresenterImpl() {
        return new FiltersFragmentPresenterImpl();
    }

    @Provides
    AdjustFragmentPresenterImpl provideAdjustFragmentPresenterImpl() {
        return new AdjustFragmentPresenterImpl();
    }

    @Provides
    TextFragmentPresenterImpl provideTextFragmentPresenterImpl() {
        return new TextFragmentPresenterImpl();
    }

    @Provides
    DrawingFragmentPresenterImpl provideBrushFragmentPresenterImpl() {
        return new DrawingFragmentPresenterImpl();
    }

    @Provides
    StickersFragmentPresenterImpl provideStickersFragmentPresenterImpl() {
        return new StickersFragmentPresenterImpl();
    }

    @Provides
    ShowStickersFragmentPresenterImpl provideShowStickersFragmentPresenterImpl() {
        return new ShowStickersFragmentPresenterImpl();
    }


}
