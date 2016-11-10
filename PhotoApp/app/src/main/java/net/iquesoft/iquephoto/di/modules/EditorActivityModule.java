package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.fragment.AdjustFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.DrawingFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.activity.EditorActivityPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.FramesFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.OverlayFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.ShowStickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.SliderControlFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.StickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.TiltShiftFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.ToolsFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.fragment.TransformFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.activity.interfaces.IEditorActivityView;

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

    @Provides
    TransformFragmentPresenterImpl provideTransformFragmentPresenterImpl() {
        return new TransformFragmentPresenterImpl();
    }

    @Provides
    SliderControlFragmentPresenterImpl provideSliderControlFragmentPresenterImpl() {
        return new SliderControlFragmentPresenterImpl();
    }
}
