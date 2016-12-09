package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.mvp.presenters.fragment.SliderControlPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.SliderControlView;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SliderControlFragment extends MvpAppCompatFragment implements SliderControlView {
    public static final String ARG_PARAM = "command";

    @InjectPresenter
    SliderControlPresenter presenter;

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

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    public static SliderControlFragment newInstance(EditorCommand editorCommand) {
        SliderControlFragment fragment = new SliderControlFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, editorCommand);

        fragment.setArguments(args);

        return fragment;
    }

    public SliderControlFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView =
                (ImageEditorView) getActivity().findViewById(R.id.editorImageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider_control, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        toolSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                currentValueTextView.setText(String.valueOf(value));

                /* TODO:
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
                }*/
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            presenter.setupTool(getArguments());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupImageEditorCommand(EditorCommand command) {
        mImageEditorView.setCommand(command);
    }

    @Override
    public void initializeCancelButton(@StringRes int toolTitle) {
        toolCancelButton.setText(toolTitle);
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
        ((EditorActivity) getActivity()).navigateBack(true);
    }

    @OnClick(R.id.toolApplyButton)
    void onClickApply() {
        // TODO: editorActivityView.getImageEditorView().applyChanges(mCommand);
        onClickBack();
    }
}
