package net.iquesoft.iquephoto.mvp.presenters.fragment;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.mvp.views.fragment.SliderControlView;
import net.iquesoft.iquephoto.ui.fragments.SliderControlFragment;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.NONE;

@InjectViewState
public class SliderControlPresenter extends MvpPresenter<SliderControlView> {
    private EditorCommand mCurrentCommand = NONE;

    public void setupTool(Bundle bundle) {
        mCurrentCommand =
                (EditorCommand) bundle.getSerializable(SliderControlFragment.ARG_PARAM);

        initializeUI();

        getViewState().setupImageEditorCommand(mCurrentCommand);

    }

    public void updateToolbarTitle() {

    }

    private void initializeUI() {
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
}
