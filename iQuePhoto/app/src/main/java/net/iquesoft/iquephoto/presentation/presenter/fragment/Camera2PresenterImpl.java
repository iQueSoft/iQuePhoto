package net.iquesoft.iquephoto.presentation.presenter.fragment;

import android.os.Handler;
import android.os.HandlerThread;

import net.iquesoft.iquephoto.core.CameraState;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.Camera2Presenter;
import net.iquesoft.iquephoto.presentation.view.activity.CameraActivityView;
import net.iquesoft.iquephoto.presentation.view.fragment.Camera2FragmentView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.CameraState.PREVIEW;

public class Camera2PresenterImpl implements Camera2Presenter {

    private boolean mIsFlashSupported;

    private String mCameraId;
    private CameraState mCameraState = PREVIEW;

    private Camera2FragmentView mView;
    private CameraActivityView mCameraActivityView;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    @Inject
    public Camera2PresenterImpl(CameraActivityView cameraActivityView) {
        mCameraActivityView = cameraActivityView;
    }

    @Override
    public void init(Camera2FragmentView view) {
        mView = view;
        mHandlerThread = new HandlerThread("CameraBackground");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }
}
