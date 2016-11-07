/*
package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.presenter.fragment.BrightnessFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.activity.interfaces.IEditorActivityView;
import net.iquesoft.iquephoto.view.fragment.interfaces.IBrightnessFragmentView;
import net.iquesoft.iquephoto.view.fragment.interfaces.IVignetteFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VignetteFragment extends BaseFragment implements IVignetteFragmentView {

    private Unbinder mUnbinder;

    @Inject
    IEditorActivityView editorActivityView;

    @Inject
    BrightnessFragmentPresenterImpl presenter;

    @BindView(R.id.brightnessSeekBar)
    DiscreteSeekBar seekBar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brightness, container, false);

        mUnbinder = ButterKnife.bind(this, v);

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                editorActivityView.getImageEditorView().setBrightnessValue(value);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.brightnessCancel)
    void onClickBack() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.brightnessApply)
    void onClickApply() {
        editorActivityView.getImageEditorView().apply(R.string.brightness);
    }
}
*/
