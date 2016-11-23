package net.iquesoft.iquephoto.ui.fragment;

import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.camera.CameraView;
import net.iquesoft.iquephoto.di.components.ICameraActivityComponent;
import net.iquesoft.iquephoto.presentation.presenter.fragment.CameraFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.fragment.CameraFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CameraFragment extends BaseFragment implements CameraFragmentView {
    @Inject
    CameraFragmentPresenterImpl presenter;

    @BindView(R.id.cameraView)
    CameraView cameraView;

    private Camera mCamera;

    private Unbinder mUnbinder;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(ICameraActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        //mContext = view.getContext();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);

        mCamera = Camera.open(0);
        mCamera.stopPreview();
        cameraView.setCamera(mCamera);
    }

    @Override
    public void onPause() {
        if (mCamera != null) {
            mCamera.stopPreview();
            cameraView.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
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