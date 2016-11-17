package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.editor.tools.AdjustFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.DrawingFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.EditorActivityPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.FramesFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.OverlayFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.ShowStickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.SliderControlFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.StickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.TiltShiftFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.ToolsFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.editor.tools.TransformFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.editor.EditorView;

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
    AdjustFragmentPresenterImpl provideAdjustFragmentPresenterImpl() {
        return new AdjustFragmentPresenterImpl();
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
