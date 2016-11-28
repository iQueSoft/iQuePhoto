package net.iquesoft.iquephoto.ui.fragment;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.di.components.EditorComponent;
import net.iquesoft.iquephoto.presentation.presenter.fragment.SliderControlFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.SliderControlView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SliderControlFragment extends BaseFragment implements SliderControlView {

    private Unbinder mUnbinder;

    private EditorCommand mCommand;

    @Inject
    EditorView editorActivityView;

    @Inject
    SliderControlFragmentPresenterImpl presenter;

    @BindView(R.id.minValueTextView)
    TextView minValueTextView;

    @BindView(R.id.currentValueTextView)
    TextView currentValueTextView;

    @BindView(R.id.maxValueTextView)
    TextView maxValueTextView;

    @BindView(R.id.toolCancelButton)
    Button toolCancelButton;

    @BindView(R.id.toolSeekBar)
    DiscreteSeekBar toolSeekBar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(EditorComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider_control, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        toolSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                currentValueTextView.setText(String.valueOf(value));

                switch (mCommand) {
                    case VIGNETTE:
                        editorActivityView.getImageEditorView().setVignetteIntensity(value);
                        break;
                    case CONTRAST:
                        editorActivityView.getImageEditorView().setContrastValue(value);
                        break;
                    case BRIGHTNESS:
                        editorActivityView.getImageEditorView().setBrightnessValue(value);
                        break;
                    case WARMTH:
                        editorActivityView.getImageEditorView().setWarmthValue(value);
                        break;
                    case SATURATION:
                        editorActivityView.getImageEditorView().setSaturationValue(value);
                        break;
                    case EXPOSURE:
                        editorActivityView.getImageEditorView().setExposureValue(value);
                        break;
                    case TINT:
                        editorActivityView.getImageEditorView().setTintValue(value);
                        break;
                    case TRANSFORM_STRAIGHTEN:

                        break;
                    case TRANSFORM_HORIZONTAL:
                        editorActivityView.getImageEditorView().setHorizontalTransformValue(value);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);

        editorActivityView.getImageEditorView().setCommand(mCommand);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void initializeCancelButton(@StringRes int toolTitle) {
        toolCancelButton.setText(toolTitle);
    }

    public void setCommand(EditorCommand command) {
        mCommand = command;
    }

    @Override
    public void initializeSlider(int minValue, int maxValue, int value) {
        toolSeekBar.setMin(minValue);
        minValueTextView.setText(String.valueOf(minValue));

        toolSeekBar.setProgress(value);
        currentValueTextView.setText(String.valueOf(value));

        toolSeekBar.setMax(maxValue);
        maxValueTextView.setText(String.valueOf(maxValue));
    }

    @OnClick(R.id.toolCancelButton)
    void onClickBack() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.toolApplyButton)
    void onClickApply() {
        editorActivityView.getImageEditorView().apply(mCommand);
        editorActivityView.navigateBack(true);
    }
}
