package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.AdjustmentFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.DrawingFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.EditorActivityPresenterImpl;
import net.iquesoft.iquephoto.presenter.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.FramesFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.OverlayFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.ShowStickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.StickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.TiltShiftFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.ToolsFragmentPresenterImpl;
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
    ToolsFragmentPresenterImpl provideToolsFragmentPresenterImpl() {
        return new ToolsFragmentPresenterImpl();
    }

    @Provides
    FiltersFragmentPresenterImpl provideFiltersFragmentPresenterImpl() {
        return new FiltersFragmentPresenterImpl();
    }

    @Provides
    AdjustmentFragmentPresenterImpl provideAdjustFragmentPresenterImpl() {
        return new AdjustmentFragmentPresenterImpl();
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

    @Provides
    OverlayFragmentPresenterImpl provideOverlayFragmentPresenterImpl() {
        return new OverlayFragmentPresenterImpl();
    }

    @Provides
    FramesFragmentPresenterImpl provideFrameFragmentPresenterImpl() {
        return new FramesFragmentPresenterImpl();
    }

    @Provides
    TiltShiftFragmentPresenterImpl provideTiltShiftFragmentPresenterImpl() {
        return new TiltShiftFragmentPresenterImpl();
    }

}
