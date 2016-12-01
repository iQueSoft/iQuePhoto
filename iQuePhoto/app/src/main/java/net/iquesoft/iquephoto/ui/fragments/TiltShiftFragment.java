package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.mvp.presenters.fragment.TiltShiftFragmentPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.TiltShiftView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TiltShiftFragment extends MvpAppCompatFragment implements TiltShiftView {
    @InjectPresenter
    TiltShiftFragmentPresenter presenter;

    private Unbinder mUnbinder;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tilt_shift, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
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
        // TODO: editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.tiltShiftApplyButton)
    void onClickApply() {
        // TODO: presenter.apply();
        //editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.tiltShiftLinearButton)
    void onClickLinear() {
        // TODO: presenter.setupTool(TILT_SHIFT_LINEAR);
    }

    @OnClick(R.id.tiltShiftRadialButton)
    void onClickRadial() {
        // TODO: presenter.setupTool(TILT_SHIFT_RADIAL);
    }
}
