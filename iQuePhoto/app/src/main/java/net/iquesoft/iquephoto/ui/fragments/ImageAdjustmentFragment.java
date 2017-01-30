package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.presentation.presenters.fragment.ImageAdjustmentPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.ImageAdjustmentView;
import net.iquesoft.iquephoto.utils.ToolbarUtil;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ImageAdjustmentFragment extends ToolFragment implements ImageAdjustmentView {
    public static final String ARG_PARAM = "command";

    @InjectPresenter
    ImageAdjustmentPresenter mPresenter;

    @ProvidePresenter
    ImageAdjustmentPresenter provideImageAdjustmentPresenter() {
        return new ImageAdjustmentPresenter(getArguments());
    }

    @BindView(R.id.minValueTextView)
    TextView mMinValueTextView;

    @BindView(R.id.currentValueTextView)
    TextView mCurrentValueTextView;

    @BindView(R.id.maxValueTextView)
    TextView mMaxValueTextView;

    @BindView(R.id.toolSeekBar)
    DiscreteSeekBar mToolSeekBar;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    public static ImageAdjustmentFragment newInstance(EditorTool editorTool) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, editorTool);

        ImageAdjustmentFragment fragment = new ImageAdjustmentFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public ImageAdjustmentFragment() {
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

        mToolSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                mPresenter.progressChanged(value);
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
        mPresenter.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void changeToolbarTitle(@StringRes int title) {
        ToolbarUtil.updateTitle(title, getActivity());
    }

    @Override
    public void changeToolbarSubtitle(@StringRes int subtitle) {
        ToolbarUtil.updateSubtitle(subtitle, getActivity());
    }

    @Override
    public void onIntensityChanged(int value) {
        Log.i("Adjustment", "Filter intensity = " + String.valueOf(value));

        mImageEditorView.setFilterIntensity(value);
    }

    @Override
    public void onBrightnessChanged(int value) {
        mImageEditorView.setBrightnessValue(value);
    }

    @Override
    public void onContrastChanged(int value) {
        mImageEditorView.setContrastValue(value);
    }

    @Override
    public void onSaturationChanged(int value) {
        mImageEditorView.setSaturationValue(value);
    }

    @Override
    public void onWarmthChanged(int value) {
        mImageEditorView.setWarmthValue(value);
    }

    @Override
    public void onStraightenTransformChanged(int value) {
        mImageEditorView.setStraightenTransformValue(value);
    }

    @Override
    public void onVignetteChanged(int value) {
        mImageEditorView.setVignetteIntensity(value);
    }

    @Override
    public void setupImageEditorCommand(EditorTool command) {
        mImageEditorView.changeTool(command);
    }

    @Override
    public void initializeSlider(int minValue, int maxValue, int value) {
        mToolSeekBar.setMin(minValue);
        mMinValueTextView.setText(String.valueOf(minValue));

        mToolSeekBar.setProgress(value);
        mCurrentValueTextView.setText(String.valueOf(value));

        mToolSeekBar.setMax(maxValue);
        mMaxValueTextView.setText(String.valueOf(maxValue));
    }
}