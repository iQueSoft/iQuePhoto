package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.activity.EditorPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.AddTextFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.AdjustPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.DrawingPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.FiltersPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.FramesPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.OverlaysPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.ShowStickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.SliderControlFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.StickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TiltShiftFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.ToolsFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TransformFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TransformHorizontalPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TransformStraightenPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TransformVerticalPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;

import dagger.Module;
import dagger.Provides;

@Module
public class EditorModule {

    private EditorView mView;

    public EditorModule(EditorView view) {
        mView = view;
    }

    @Provides
    EditorPresenterImpl provideEditorActivityPresenterImpl(EditorView view) {
        return new EditorPresenterImpl(view);
    }

    @Provides
    EditorView provideView() {
        return mView;
    }

    @Provides
    ToolsFragmentPresenterImpl provideToolsFragmentPresenterImpl() {
        return new ToolsFragmentPresenterImpl(mView);
    }

    @Provides
    FiltersPresenterImpl provideFiltersFragmentPresenterImpl() {
        return new FiltersPresenterImpl(mView);
    }

    @Provides
    AdjustPresenterImpl provideAdjustFragmentPresenterImpl() {
        return new AdjustPresenterImpl(mView);
    }

    @Provides
    AddTextFragmentPresenterImpl provideAddTextFragmentPresenterImpl() {
        return new AddTextFragmentPresenterImpl(mView);
    }

    @Provides
    DrawingPresenterImpl provideBrushFragmentPresenterImpl() {
        return new DrawingPresenterImpl(mView);
    }

    @Provides
    StickersFragmentPresenterImpl provideStickersFragmentPresenterImpl() {
        return new StickersFragmentPresenterImpl(mView);
    }

    @Provides
    ShowStickersFragmentPresenterImpl provideShowStickersFragmentPresenterImpl() {
        return new ShowStickersFragmentPresenterImpl(mView);
    }

    @Provides
    OverlaysPresenterImpl provideOverlayFragmentPresenterImpl() {
        return new OverlaysPresenterImpl(mView);
    }

    @Provides
    FramesPresenterImpl provideFrameFragmentPresenterImpl() {
        return new FramesPresenterImpl(mView);
    }

    @Provides
    TiltShiftFragmentPresenterImpl provideTiltShiftFragmentPresenterImpl() {
        return new TiltShiftFragmentPresenterImpl(mView);
    }

    @Provides
    TransformFragmentPresenterImpl provideTransformFragmentPresenterImpl() {
        return new TransformFragmentPresenterImpl(mView);
    }

    @Provides
    TransformHorizontalPresenterImpl transformHorizontalFragmentPresenterImpl() {
        return new TransformHorizontalPresenterImpl(mView);
    }

    @Provides
    TransformStraightenPresenterImpl provideTransformStraightenPresenterImpl() {
        return new TransformStraightenPresenterImpl(mView);
    }

    @Provides
    TransformVerticalPresenterImpl provideTransformVerticalPresenterImpl() {
        return new TransformVerticalPresenterImpl(mView);
    }

    @Provides
    SliderControlFragmentPresenterImpl provideSliderControlFragmentPresenterImpl() {
        return new SliderControlFragmentPresenterImpl(mView);
    }


}
