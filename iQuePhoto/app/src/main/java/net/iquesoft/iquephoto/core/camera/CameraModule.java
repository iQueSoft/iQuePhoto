package net.iquesoft.iquephoto.core.camera;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.widget.Toast;

import net.iquesoft.iquephoto.tasks.CameraImageSaver;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static net.iquesoft.iquephoto.core.camera.CameraState.PICTURE_TAKEN;
import static net.iquesoft.iquephoto.core.camera.CameraState.PREVIEW;
import static net.iquesoft.iquephoto.core.camera.CameraState.WAITING_LOCK;
import static net.iquesoft.iquephoto.core.camera.CameraState.WAITING_NON_PRECAPTURE;
import static net.iquesoft.iquephoto.core.camera.CameraState.WAITING_PRECAPTURE;

public class CameraModule implements ImageReader.OnImageAvailableListener {
    private static final int MAX_PREVIEW_WIDTH = 1920;
    private static final int MAX_PREVIEW_HEIGHT = 1080;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private int mSensorOrientation;

    private boolean mIsFlashSupported;

    private String mCameraId;

    private File mFile;

    private Size mSize;

    private Semaphore mSemaphore;

    private Activity mActivity;

    private CameraView mCameraView;

    private CameraDevice mCamera;
    private CameraState mCameraState = PREVIEW;
    private CameraCaptureSession mCameraCaptureSession;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private ImageReader mImageReader;

    private CaptureRequest mCaptureRequest;
    private CaptureRequest.Builder mCaptureRequestBuilder;

    private CameraDevice.StateCallback mCameraStateCallback;
    private CameraCaptureSession.CaptureCallback mCaptureCallback;

    public CameraModule(Activity activity, CameraView cameraView) {
        mActivity = activity;
        mCameraView = cameraView;

        mFile = new File(mActivity.getExternalFilesDir(null), "pic.jpg");
        mSemaphore = new Semaphore(1);

        initializeCameraStateCallback();

        initializeCaptureCallback();

    }

    private void initializeCameraStateCallback() {
        mCameraStateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                mSemaphore.release();
                mCamera = cameraDevice;
                createCameraPreviewSession();

                Log.i("Camera 2", "onOpened");
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                mSemaphore.release();
                cameraDevice.close();
                mCamera = null;

                Log.i("Camera 2", "onDisconnected");
            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, int i) {
                Log.i("Camera 2", "onError");
            }
        };
    }

    private void initializeCaptureCallback() {
        mCaptureCallback = new CameraCaptureSession.CaptureCallback() {

            @Override
            public void onCaptureProgressed(@NonNull CameraCaptureSession session,
                                            @NonNull CaptureRequest request,
                                            @NonNull CaptureResult partialResult) {
                process(partialResult);
            }

            @Override
            public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                           @NonNull CaptureRequest request,
                                           @NonNull TotalCaptureResult result) {
                process(result);
            }
        };
    }

    @Override
    public void onImageAvailable(ImageReader imageReader) {
        mHandler.post(new CameraImageSaver(imageReader.acquireNextImage(), mFile));
    }

    public void openCamera(int width, int height) {
        setUpCameraOutputs(width, height);
        configureTransform(width, height);

        CameraManager cameraManager = (CameraManager) mActivity.getSystemService(Context.CAMERA_SERVICE);
        try {
            if (!mSemaphore.tryAcquire(2500, TimeUnit.MILLISECONDS))
                throw new RuntimeException("Time out waiting to lock camera opening.");

            cameraManager.openCamera(mCameraId, mCameraStateCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        }
    }

    public void closeCamera() {
        try {
            mSemaphore.acquire();
            if (null != mCameraCaptureSession) {
                mCameraCaptureSession.close();
                mCameraCaptureSession = null;
            }
            if (null != mCamera) {
                mCamera.close();
                mCamera = null;
            }
            if (null != mImageReader) {
                mImageReader.close();
                mImageReader = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } finally {
            mSemaphore.release();
        }
    }

    public void startHandlerThread() {
        mHandlerThread = new HandlerThread("CameraBackground");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public void stopHandlerThread() {
        mHandlerThread.quitSafely();
        try {
            mHandlerThread.join();
            mHandlerThread = null;
            mHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void lockFocus() {
        try {
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CameraMetadata.CONTROL_AF_TRIGGER_START);

            mCameraState = WAITING_LOCK;
            mCameraCaptureSession.capture(mCaptureRequestBuilder.build(), mCaptureCallback,
                    mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void unlockFocus() {
        try {
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
            setAutoFlash(mCaptureRequestBuilder);
            mCameraCaptureSession.capture(mCaptureRequestBuilder.build(), mCaptureCallback,
                    mHandler);

            mCameraState = PREVIEW;
            mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, mCaptureCallback,
                    mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void process(CaptureResult captureResult) {
        switch (mCameraState) {
            case PREVIEW:
                break;
            case WAITING_LOCK:
                Integer afState = captureResult.get(CaptureResult.CONTROL_AF_STATE);
                if (afState == null)
                    captureStillPicture();
                else if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState ||
                        CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState) {

                    Integer aeState = captureResult.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null ||
                            aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
                        mCameraState = PICTURE_TAKEN;
                        captureStillPicture();
                    } else
                        runPrecaptureSequence();

                }
                break;
            case WAITING_PRECAPTURE: {
                Integer aeState = captureResult.get(CaptureResult.CONTROL_AE_STATE);
                if (aeState == null ||
                        aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE ||
                        aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED) {
                    mCameraState = WAITING_NON_PRECAPTURE;
                }
            }
            break;
            case WAITING_NON_PRECAPTURE:
                Integer aeState = captureResult.get(CaptureResult.CONTROL_AE_STATE);
                if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                    mCameraState = PICTURE_TAKEN;
                    captureStillPicture();
                }
                break;
        }
    }

    private void setUpCameraOutputs(int width, int height) {
        CameraManager manager = (CameraManager) mActivity.getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics
                        = manager.getCameraCharacteristics(cameraId);

                // We don't use a front facing camera in this sample.
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }

                StreamConfigurationMap map = characteristics.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map == null) {
                    continue;
                }

                // For still image captures, we use the largest available size.
                Size largest = Collections.max(
                        Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                        new CameraView.CompareSizesByArea());
                mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                        ImageFormat.JPEG, /*maxImages*/2);
                mImageReader.setOnImageAvailableListener(
                        this, mHandler);

                int displayRotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();

                mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);

                boolean swappedDimensions = false;
                switch (displayRotation) {
                    case Surface.ROTATION_0:
                    case Surface.ROTATION_180:
                        if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                            swappedDimensions = true;
                        }
                        break;
                    case Surface.ROTATION_90:
                    case Surface.ROTATION_270:
                        if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                            swappedDimensions = true;
                        }
                        break;
                    default:
                        Log.e(CameraView.class.getSimpleName(),
                                "Display rotation is invalid: " + displayRotation);
                }

                Point displaySize = new Point();
                mActivity.getWindowManager().getDefaultDisplay().getSize(displaySize);
                int rotatedPreviewWidth = width;
                int rotatedPreviewHeight = height;
                int maxPreviewWidth = displaySize.x;
                int maxPreviewHeight = displaySize.y;

                if (swappedDimensions) {
                    rotatedPreviewWidth = height;
                    rotatedPreviewHeight = width;
                    maxPreviewWidth = displaySize.y;
                    maxPreviewHeight = displaySize.x;
                }

                if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                    maxPreviewWidth = MAX_PREVIEW_WIDTH;
                }

                if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                    maxPreviewHeight = MAX_PREVIEW_HEIGHT;
                }

                mSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                        rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                        maxPreviewHeight, largest);

                int orientation = mActivity.getResources().getConfiguration().orientation;

                if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                    mCameraView.setAspectRatio(mSize.getWidth(), mSize.getHeight());
                else
                    mCameraView.setAspectRatio(mSize.getHeight(), mSize.getWidth());

                Boolean available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                mIsFlashSupported = available == null ? false : available;

                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void takePicture() {
        lockFocus();
    }

    private int getOrientation(int rotation) {
        return (ORIENTATIONS.get(rotation) + mSensorOrientation + 270) % 360;
    }

    private void runPrecaptureSequence() {
        try {
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
                    CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START);

            mCameraState = WAITING_PRECAPTURE;
            mCameraCaptureSession.capture(mCaptureRequestBuilder.build(), mCaptureCallback,
                    mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void captureStillPicture() {
        try {
            if (null == mActivity || null == mCamera) {
                return;
            }

            final CaptureRequest.Builder captureBuilder =
                    mCamera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(mImageReader.getSurface());

            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            setAutoFlash(captureBuilder);

            int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getOrientation(rotation));

            CameraCaptureSession.CaptureCallback CaptureCallback
                    = new CameraCaptureSession.CaptureCallback() {

                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                               @NonNull CaptureRequest request,
                                               @NonNull TotalCaptureResult result) {
                    Toast.makeText(mActivity, "Saved: " + mFile.toString(), Toast.LENGTH_SHORT).show();
                    unlockFocus();
                }
            };

            mCameraCaptureSession.stopRepeating();
            mCameraCaptureSession.capture(captureBuilder.build(), CaptureCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void createCameraPreviewSession() {
        try {
            SurfaceTexture texture = mCameraView.getSurfaceTexture();
            assert texture != null;

            texture.setDefaultBufferSize(mSize.getWidth(), mSize.getHeight());

            Surface surface = new Surface(texture);

            mCaptureRequestBuilder
                    = mCamera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCaptureRequestBuilder.addTarget(surface);

            mCamera.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            if (null == mCamera)
                                return;

                            mCameraCaptureSession = cameraCaptureSession;
                            try {
                                mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

                                setAutoFlash(mCaptureRequestBuilder);

                                mCaptureRequest = mCaptureRequestBuilder.build();
                                mCameraCaptureSession.setRepeatingRequest(mCaptureRequest,
                                        mCaptureCallback, mHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(
                                @NonNull CameraCaptureSession cameraCaptureSession) {
                            Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }, null
            );
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void setAutoFlash(CaptureRequest.Builder requestBuilder) {
        if (mIsFlashSupported) {
            requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
        }
    }

    private static Size chooseOptimalSize(Size[] choices,
                                          int textureViewWidth,
                                          int textureViewHeight,
                                          int maxWidth,
                                          int maxHeight,
                                          Size aspectRatio) {

        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        // Collect the supported resolutions that are smaller than the preview Surface
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                    option.getHeight() == option.getWidth() * h / w) {
                if (option.getWidth() >= textureViewWidth &&
                        option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        if (bigEnough.size() > 0)
            return Collections.min(bigEnough, new CameraView.CompareSizesByArea());
        else if (notBigEnough.size() > 0)
            return Collections.max(notBigEnough, new CameraView.CompareSizesByArea());
        else
            return choices[0];
    }

    public void configureTransform(int viewWidth, int viewHeight) {
        if (null == mSize || null == mActivity)
            return;

        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();

        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mSize.getHeight(), mSize.getWidth());

        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();

        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mSize.getHeight(),
                    (float) viewWidth / mSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == rotation)
            matrix.postRotate(180, centerX, centerY);

        mCameraView.setTransform(matrix);
    }
}
