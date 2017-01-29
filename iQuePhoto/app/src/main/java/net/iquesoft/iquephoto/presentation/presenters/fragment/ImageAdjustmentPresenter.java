package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.presentation.views.fragment.ImageAdjustmentView;
import net.iquesoft.iquephoto.ui.fragments.ImageAdjustmentFragment;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.NONE;

@InjectViewState
public class ImageAdjustmentPresenter extends MvpPresenter<ImageAdjustmentView> {
    private EditorTool mCurrentCommand = NONE;

    public ImageAdjustmentPresenter(@NonNull Bundle bundle) {
        mCurrentCommand =
                (EditorTool) bundle.getSerializable(ImageAdjustmentFragment.ARG_PARAM);

        Log.i("Slider", "Current tool = " + mCurrentCommand.name());

        setupSlider();
    }

    public void onResume() {
        switch (mCurrentCommand) {
            case FILTERS:
                getViewState().changeToolbarSubtitle(R.string.intensity);
                break;
            case BRIGHTNESS:
                getViewState().changeToolbarSubtitle(R.string.brightness);
                break;
            case CONTRAST:
                getViewState().changeToolbarSubtitle(R.string.contrast);
                break;
            case WARMTH:
                getViewState().changeToolbarSubtitle(R.string.warmth);
                break;
            case TRANSFORM_STRAIGHTEN:
                getViewState().changeToolbarSubtitle(R.string.transform_straighten);
                break;
        }

        getViewState().changeToolbarTitle(getToolTitle());
        getViewState().setupImageEditorCommand(mCurrentCommand);
    }

    public void progressChanged(int value) {
        switch (mCurrentCommand) {
            case BRIGHTNESS:
                getViewState().onBrightnessValueChanged(value);
                break;
            case CONTRAST:
                getViewState().onContrastValueChanged(value);
                break;
            case WARMTH:
                getViewState().onWarmthValueChanged(value);
                break;
            case TRANSFORM_STRAIGHTEN:
                getViewState().onStraightenValueChanged(value);
                break;
            case VIGNETTE:
                getViewState().onVignetteValueChanged(value);
                break;
        }
    }

    private void setupSlider() {
        switch (mCurrentCommand) {
            case BRIGHTNESS:
                getViewState().initializeSlider(-100, 100, 0);
                break;
            case CONTRAST:
                getViewState().initializeSlider(-100, 100, 0);
                break;
            case SATURATION:
                getViewState().initializeSlider(-100, 100, 0);
                break;
            case WARMTH:
                getViewState().initializeSlider(-100, 100, 0);
                break;
            case EXPOSURE:
                getViewState().initializeSlider(-100, 100, 0);
                break;
            case VIGNETTE:
                getViewState().initializeSlider(-100, 100, 70);
                break;
            case TINT:
                getViewState().initializeSlider(-100, 100, 0);
                break;
            case TRANSFORM_HORIZONTAL:
                getViewState().initializeSlider(-30, 30, 0);
                break;
            case TRANSFORM_STRAIGHTEN:
                getViewState().initializeSlider(-30, 30, 0);
                break;
            case TRANSFORM_VERTICAL:
                getViewState().initializeSlider(-30, 30, 0);
                break;
        }
    }

    private int getToolTitle() {
        int title = R.string.adjust;

        switch (mCurrentCommand) {
            case FILTERS:
                title = R.string.filters;
                break;
            case TRANSFORM_STRAIGHTEN:
                title = R.string.transform;
                break;
            case VIGNETTE:
                title = R.string.vignette;
                break;
        }

        return title;
    }
}