package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.mvp.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.EditorComponent;
import net.iquesoft.iquephoto.mvp.presenters.fragment.TiltShiftFragmentPresenter;
import net.iquesoft.iquephoto.mvp.views.activity.EditorView;
import net.iquesoft.iquephoto.mvp.views.fragment.TiltShiftView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TiltShiftFragment extends BaseFragment implements TiltShiftView {
    private Unbinder mUnbinder;

    @Inject
    EditorView editorActivityView;

    @InjectPresenter
    TiltShiftFragmentPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(EditorComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tilt_shift, container, false);

        mUnbinder = ButterKnife.bind(this, v);

        return v;
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

    @OnClick(R.id.tiltShiftCancelButton)
    void onClickCancel() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.tiltShiftApplyButton)
    void onClickApply() {
        // TODO: presenter.apply();
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.tiltShiftLinearButton)
    void onClickLinear() {
        // TODO: presenter.setCommand(TILT_SHIFT_LINEAR);
    }

    @OnClick(R.id.tiltShiftRadialButton)
    void onClickRadial() {
        // TODO: presenter.setCommand(TILT_SHIFT_RADIAL);
    }
}
