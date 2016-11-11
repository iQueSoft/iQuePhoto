package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.presenter.fragment.interfaces.ISliderControlFragmentPresenter;
import net.iquesoft.iquephoto.presenter.fragment.interfaces.ITransformFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.ISliderControlFragmentView;
import net.iquesoft.iquephoto.view.fragment.interfaces.ITransformFragmentView;

import javax.inject.Inject;

public class SliderControlFragmentPresenterImpl implements ISliderControlFragmentPresenter {

    private EditorCommand mCommand;

    private ISliderControlFragmentView mView;

    @Inject
    public SliderControlFragmentPresenterImpl() {

    }

    @Override
    public void init(ISliderControlFragmentView view) {
        mView = view;
    }

    @Override
    public void setCommand(EditorCommand command) {
        mCommand = command;

        switch (mCommand) {
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
            case VIGNETTE:
                mView.initializeCancelButton(R.string.vignette);
                mView.initializeSlider(-100, 100, 70);
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
    }
}
