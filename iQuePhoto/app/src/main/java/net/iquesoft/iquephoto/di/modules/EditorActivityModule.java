package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presentation.presenter.fragment.AddTextFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.AdjustPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.DrawingFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.activity.EditorActivityPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.FramesFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.OverlayFragmentPresenterImpl;
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
import net.iquesoft.iquephoto.ui.fragment.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragment.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragment.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragment.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.FramesFragment;
import net.iquesoft.iquephoto.ui.fragment.OverlayFragment;
import net.iquesoft.iquephoto.ui.fragment.StickersFragment;
import net.iquesoft.iquephoto.ui.fragment.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragment.ToolsFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformFragment;

import javax.inject.Singleton;

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
        return new ToolsFragmentPresenterImpl(mView);
    }

    @Provides
    FiltersFragmentPresenterImpl provideFiltersFragmentPresenterImpl() {
        return new FiltersFragmentPresenterImpl(mView);
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
    DrawingFragmentPresenterImpl provideBrushFragmentPresenterImpl() {
        return new DrawingFragmentPresenterImpl(mView);
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
    OverlayFragmentPresenterImpl provideOverlayFragmentPresenterImpl() {
        return new OverlayFragmentPresenterImpl(mView);
    }

    @Provides
    FramesFragmentPresenterImpl provideFrameFragmentPresenterImpl() {
        return new FramesFragmentPresenterImpl(mView);
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
