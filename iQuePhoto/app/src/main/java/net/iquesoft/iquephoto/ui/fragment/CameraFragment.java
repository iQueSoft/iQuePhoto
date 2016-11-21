package net.iquesoft.iquephoto.ui.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.ICameraActivityComponent;
import net.iquesoft.iquephoto.presentation.presenter.fragment.CameraFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.fragment.CameraFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CameraFragment extends BaseFragment implements CameraFragmentView {

    private Context mContext;

    private Unbinder mUnbinder;

    private Camera mCamera;

    @Inject
    CameraFragmentPresenterImpl presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(ICameraActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mContext = view.getContext();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void changeFilter(Paint paint) {

    }
}