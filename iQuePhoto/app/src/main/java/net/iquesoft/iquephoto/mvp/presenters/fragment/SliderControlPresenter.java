package net.iquesoft.iquephoto.mvp.presenters.fragment;

import android.os.Bundle;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.mvp.views.fragment.SliderControlView;
import net.iquesoft.iquephoto.ui.fragments.SliderControlFragment;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.NONE;

@InjectViewState
public class SliderControlPresenter extends MvpPresenter<SliderControlView> {
    private EditorTool mCurrentCommand = NONE;

    public void setupTool(Bundle bundle) {
        mCurrentCommand =
                (EditorTool) bundle.getSerializable(SliderControlFragment.ARG_PARAM);

        initializeUI();
    }

    public void onResume() {
        getViewState().changeToolbarTitle(getToolTitle());
        getViewState().setupImageEditorCommand(mCurrentCommand);
    }

    public void progressChanged(int value) {
        switch (mCurrentCommand) {
            case TRANSFORM_STRAIGHTEN:
                getViewState().onStraightenValueChanged(value);
                break;
        }
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

    @StringRes
    private int getToolTitle() {
        int title = 0;

        switch (mCurrentCommand) {
            case TRANSFORM_STRAIGHTEN:
                title = R.string.transform_straighten;
                break;
            case VIGNETTE:
                title = R.string.vignette;
        }

        return title;
    }
}
