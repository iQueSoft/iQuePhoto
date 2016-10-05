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

    private IEditorActivityView view;

    public EditorActivityModule(IEditorActivityView view) {
        this.view = view;
    }

    @Provides
    public EditorActivityPresenterImpl provideEditorActivityPresenterImpl(IEditorActivityView view) {
        return new EditorActivityPresenterImpl(view);
    }

    @Provides
    public IEditorActivityView provideView() {
        return view;
    }

    @Provides
    public FiltersFragmentPresenterImpl provideFiltersFragmentPresenterImpl() {
        return new FiltersFragmentPresenterImpl();
    }

    @Provides
    public AdjustFragmentPresenterImpl provideAdjustFragmentPresenterImpl() {
        return new AdjustFragmentPresenterImpl();
    }

    @Provides
    public TextFragmentPresenterImpl provideTextFragmentPresenterImpl() {
        return new TextFragmentPresenterImpl();
    }

    @Provides
    public DrawingFragmentPresenterImpl provideBrushFragmentPresenterImpl() {
        return new DrawingFragmentPresenterImpl();
    }

    @Provides
    public StickersFragmentPresenterImpl provideStickersFragmentPresenterImpl() {
        return new StickersFragmentPresenterImpl();
    }

    @Provides
    public ShowStickersFragmentPresenterImpl provideShowStickersFragmentPresenterImpl() {
        return new ShowStickersFragmentPresenterImpl();
    }


}
