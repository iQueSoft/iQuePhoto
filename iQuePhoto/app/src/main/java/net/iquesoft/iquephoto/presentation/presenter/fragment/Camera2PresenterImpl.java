package net.iquesoft.iquephoto.presentation.presenter.fragment;

import android.os.Handler;
import android.os.HandlerThread;

import net.iquesoft.iquephoto.core.CameraState;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.Camera2Presenter;
import net.iquesoft.iquephoto.presentation.view.activity.CameraView;
import net.iquesoft.iquephoto.presentation.view.fragment.Camera2View;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.CameraState.PREVIEW;

public class Camera2PresenterImpl implements Camera2Presenter {

    private boolean mIsFlashSupported;

    private String mCameraId;
    private CameraState mCameraState = PREVIEW;

    private Camera2View mView;
    private CameraView mCameraView;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    @Inject
    public Camera2PresenterImpl(CameraView cameraView) {
        mCameraView = cameraView;
    }

    @Override
    public void init(Camera2View view) {
        mView = view;
        mHandlerThread = new HandlerThread("CameraBackground");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }
}
