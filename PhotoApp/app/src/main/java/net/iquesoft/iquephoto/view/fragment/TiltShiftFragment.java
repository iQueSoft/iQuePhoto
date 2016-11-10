package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.presenter.fragment.TiltShiftFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.activity.interfaces.IEditorActivityView;
import net.iquesoft.iquephoto.view.fragment.interfaces.ITiltShiftFragmentView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TiltShiftFragment extends BaseFragment implements ITiltShiftFragmentView {
    private Unbinder mUnbinder;

    @Inject
    IEditorActivityView editorActivityView;

    @Inject
    TiltShiftFragmentPresenterImpl presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
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
        presenter.init(this);
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
        //TODO: Apply Tilt Shift.
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.tiltShiftLinearButton)
    void onClickLinear() {
        //TODO: Switch to Linear Tilt Shift.
    }

    void onClickRadial() {
        //TODO: Switch to Radial Tilt Shift.
    }
}
