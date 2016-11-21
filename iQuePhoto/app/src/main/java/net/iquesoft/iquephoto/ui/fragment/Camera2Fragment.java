package net.iquesoft.iquephoto.ui.fragment;

import android.content.Context;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.media.ImageReader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.AutoFitCameraView;
import net.iquesoft.iquephoto.di.components.ICameraActivityComponent;
import net.iquesoft.iquephoto.presentation.presenter.fragment.Camera2PresenterImpl;
import net.iquesoft.iquephoto.presentation.view.fragment.Camera2View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Camera2Fragment extends BaseFragment implements Camera2View {
    @Inject
    Camera2PresenterImpl presenter;

    @BindView(R.id.camera2View)
    AutoFitCameraView autoFitCameraView;

    private Context mContext;

    private Unbinder mUnbinder;

    /*private ImageReader mImageReader;

    private CameraDevice mCameraDevice;

    private CameraCaptureSession mCaptureSession;*/

    //private Semaphore mCameraOpenCloseLock = new Semaphore(1);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(ICameraActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_2, container, false);

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
        
    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void changeFilter(Paint paint) {
        autoFitCameraView.setLayerPaint(paint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            if (null != mCaptureSession) {
                mCaptureSession.close();
                mCaptureSession = null;
            }
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (null != mImageReader) {
                mImageReader.close();
                mImageReader = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } finally {
            mCameraOpenCloseLock.release();
        }
    }*/
}