package net.iquesoft.iquephoto.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TransformVerticalPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.TransformVerticalView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.EditorCommand.TRANSFORM_VERTICAL;

public class TransformVerticalFragment extends BaseFragment implements TransformVerticalView {

    private Unbinder mUnbinder;

    @Inject
    EditorView editorActivityView;

    @Inject
    TransformVerticalPresenterImpl presenter;

    @BindView(R.id.transformVerticalValueTextView)
    TextView currentValueTextView;

    @BindView(R.id.transformVerticalSeekBar)
    DiscreteSeekBar toolSeekBar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transform_vertical, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        toolSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                currentValueTextView.setText(String.valueOf(value));
                editorActivityView.getImageEditorView().setTransformVerticalValue(value);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.transformVerticalBackButton)
    void onClickBack() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.transformVerticalApplyButton)
    void onClickApply() {
        editorActivityView.getImageEditorView().apply(TRANSFORM_VERTICAL);
        editorActivityView.navigateBack(true);
    }
}
