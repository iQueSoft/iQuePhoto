package net.iquesoft.iquephoto.mvp.presenters.fragment;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.mvp.views.fragment.SliderControlView;
import net.iquesoft.iquephoto.ui.fragments.SliderControlFragment;

@InjectViewState
public class SliderControlPresenter extends MvpPresenter<SliderControlView> {

    public void setupTool(Bundle bundle) {
        EditorCommand command =
                (EditorCommand) bundle.getSerializable(SliderControlFragment.ARG_PARAM);

        if (command != null) {
            switch (command) {
                case BRIGHTNESS:
                    getViewState().initializeCancelButton(R.string.brightness);
                    getViewState().initializeSlider(-100, 100, 0);
                    break;
                case CONTRAST:
                    getViewState().initializeCancelButton(R.string.contrast);
                    getViewState().initializeSlider(-100, 100, 0);
                    break;
                case SATURATION:
                    getViewState().initializeCancelButton(R.string.saturation);
                    getViewState().initializeSlider(-100, 100, 0);
                    break;
                case WARMTH:
                    getViewState().initializeCancelButton(R.string.warmth);
                    getViewState().initializeSlider(-100, 100, 0);
                    break;
                case EXPOSURE:
                    getViewState().initializeCancelButton(R.string.exposure);
                    getViewState().initializeSlider(-100, 100, 0);
                    break;
                case VIGNETTE:
                    getViewState().initializeCancelButton(R.string.vignette);
                    getViewState().initializeSlider(-100, 100, 70);
                    break;
                case TINT:
                    getViewState().initializeCancelButton(R.string.tint);
                    getViewState().initializeSlider(-100, 100, 0);
                    break;
                case TRANSFORM_HORIZONTAL:
                    getViewState().initializeCancelButton(R.string.transform_horizontal);
                    getViewState().initializeSlider(-30, 30, 0);
                    break;
                case TRANSFORM_STRAIGHTEN:
                    getViewState().initializeCancelButton(R.string.transform_straighten);
                    getViewState().initializeSlider(-30, 30, 0);
                    break;
                case TRANSFORM_VERTICAL:
                    getViewState().initializeCancelButton(R.string.transform_vertical);
                    getViewState().initializeSlider(-30, 30, 0);
                    break;
            }
        }
    }
}
