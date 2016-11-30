package net.iquesoft.iquephoto.ui.fragments;

import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.camera.CameraView;
import net.iquesoft.iquephoto.mvp.presenters.fragment.CameraFragmentPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.CameraFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CameraFragment extends MvpAppCompatFragment implements CameraFragmentView {
    @InjectPresenter
    CameraFragmentPresenter presenter;

    @BindView(R.id.cameraView)
    CameraView cameraView;

    private Camera mCamera;

    private Unbinder mUnbinder;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

        safeCameraOpen(0);
    }

    @Override
    public void onPause() {
        releaseCameraAndPreview();
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

    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;

        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCameraAndPreview() {
        cameraView.setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}