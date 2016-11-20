package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.SliderControlView;

import javax.inject.Inject;

public class SliderControlFragmentPresenterImpl implements SliderControlPresenter {
    private SliderControlView mView;
    private EditorView mEditorView;

    @Inject
    public SliderControlFragmentPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(SliderControlView view) {
        mView = view;
    }

    @Override
    public void setCommand(EditorCommand command) {
        switch (command) {
            case BRIGHTNESS:
                mView.initializeCancelButton(R.string.brightness);
                mView.initializeSlider(-100, 100, 0);
                break;
            case CONTRAST:
                mView.initializeCancelButton(R.string.contrast);
                mView.initializeSlider(-100, 100, 0);
                break;
            case SATURATION:
                mView.initializeCancelButton(R.string.saturation);
                mView.initializeSlider(-100, 100, 0);
                break;
            case WARMTH:
                mView.initializeCancelButton(R.string.warmth);
                mView.initializeSlider(-100, 100, 0);
                break;
            case EXPOSURE:
                mView.initializeCancelButton(R.string.exposure);
                mView.initializeSlider(-100, 100, 0);
                break;
            case VIGNETTE:
                mView.initializeCancelButton(R.string.vignette);
                mView.initializeSlider(-100, 100, 70);
                break;
            case TINT:
                mView.initializeCancelButton(R.string.tint);
                mView.initializeSlider(-100, 100, 0);
                break;
            case TRANSFORM_HORIZONTAL:
                mView.initializeCancelButton(R.string.transform_horizontal);
                mView.initializeSlider(-30, 30, 0);
                break;
            case TRANSFORM_STRAIGHTEN:
                mView.initializeCancelButton(R.string.transform_straighten);
                mView.initializeSlider(-30, 30, 0);
                break;
            case TRANSFORM_VERTICAL:
                mView.initializeCancelButton(R.string.transform_vertical);
                mView.initializeSlider(-30, 30, 0);
                break;
        }

        mEditorView.setEditorCommand(command);
        mEditorView.setupToolFragment(command);
    }
}
