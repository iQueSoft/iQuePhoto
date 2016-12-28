package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.mvp.common.BaseToolFragment;
import net.iquesoft.iquephoto.mvp.presenters.fragment.SliderControlPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.SliderControlView;
import net.iquesoft.iquephoto.util.ActivityUtil;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SliderControlFragment extends BaseToolFragment implements SliderControlView {
    public static final String ARG_PARAM = "command";

    @InjectPresenter
    SliderControlPresenter presenter;

    @BindView(R.id.minValueTextView)
    TextView minValueTextView;

    @BindView(R.id.currentValueTextView)
    TextView currentValueTextView;

    @BindView(R.id.maxValueTextView)
    TextView maxValueTextView;

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
                (ImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider_control, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        toolSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                currentValueTextView.setText(String.valueOf(value));
                presenter.progressChanged(value);
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
        presenter.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void changeToolbarTitle(@StringRes int title) {
        ActivityUtil.updateToolbarTitle(title, getActivity());
    }

    @Override
    public void onStraightenValueChanged(int value) {
        mImageEditorView.setTransformStraightenValue(value);
    }

    @Override
    public void onVignetteValueChanged(int value) {
        mImageEditorView.setVignetteIntensity(value);
    }

    @Override
    public void setupImageEditorCommand(EditorCommand command) {
        mImageEditorView.setCommand(command);
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
}
