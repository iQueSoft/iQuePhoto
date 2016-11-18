package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.fragment.AdjustPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.DrawingFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.activity.EditorActivityPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.FramesFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.OverlayFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.ShowStickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.SliderControlFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.StickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TiltShiftFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.ToolsFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TransformFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;

import dagger.Module;
import dagger.Provides;

@Module
public class EditorActivityModule {

    private EditorView mView;

    public EditorActivityModule(EditorView view) {
        mView = view;
    }

    @Provides
    EditorActivityPresenterImpl provideEditorActivityPresenterImpl(EditorView view) {
        return new EditorActivityPresenterImpl(view);
    }

    @Provides
    EditorView provideView() {
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
    AdjustPresenterImpl provideAdjustFragmentPresenterImpl() {
        return new AdjustPresenterImpl();
    }

    @Provides
    TextFragmentPresenterImpl provideTextFragmentPresenterImpl() {
        return new TextFragmentPresenterImpl(mView);
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
