package net.iquesoft.iquephoto.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.presenter.fragment.SliderControlFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.activity.interfaces.IEditorActivityView;
import net.iquesoft.iquephoto.view.fragment.interfaces.ISliderControlFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.EditorCommand.VIGNETTE;

@SuppressLint("ValidFragment")
public class SliderControlFragment extends BaseFragment implements ISliderControlFragmentView {

    private Unbinder mUnbinder;

    private EditorCommand mCommand;

    @Inject
    IEditorActivityView editorActivityView;

    @Inject
    SliderControlFragmentPresenterImpl presenter;

    @BindView(R.id.toolCancelButton)
    Button toolCancelButton;

    @BindView(R.id.toolSeekBar)
    DiscreteSeekBar toolSeekBar;

    public static SliderControlFragment newInstance(EditorCommand command) {
        return new SliderControlFragment(command);
    }

    private SliderControlFragment(EditorCommand command) {
        mCommand = command;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_slider_control, container, false);

        mUnbinder = ButterKnife.bind(this, v);

        toolSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                switch (mCommand) {
                    case VIGNETTE:
                        editorActivityView.getImageEditorView().setVignetteIntensity(value);
                        break;
                    case BRIGHTNESS:
                        editorActivityView.getImageEditorView().setBrightnessValue(value);
                        break;
                    case TRANSFORM_STRAIGHTEN:
                        editorActivityView.getImageEditorView().setStraightenValue(value);
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

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
        presenter.setCommand(mCommand);

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

    @Override
    public void initializeSlider(int minValue, int maxValue, int value) {
        toolSeekBar.setMin(minValue);
        toolSeekBar.setMax(maxValue);
        toolSeekBar.setProgress(value);
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
