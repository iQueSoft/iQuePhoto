package net.iquesoft.iquephoto.core.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.View;

import net.iquesoft.iquephoto.core.CameraListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.ACTION_UP;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2View extends TextureView implements CameraListener,
        TextureView.SurfaceTextureListener,
        TextureView.OnKeyListener,
        Comparator<Size> {

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;

    private Context mContext;

    private Camera2Module mCameraModule;

    public Camera2View(Context context) {
        super(context);
        initialize(context);
    }

    public Camera2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initialize(context);
    }

    public Camera2View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        mContext = context;

        setSurfaceTextureListener(this);

        Log.i("Camera 2", "initialized");
    }

    public void setCameraModule(Camera2Module cameraModule) {
        mCameraModule = cameraModule;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //if (0 == mRatioWidth || 0 == mRatioHeight)
        setMeasuredDimension(width, height);
        /*else {
            if (width < height * mRatioWidth / mRatioHeight)
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
            else
                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
        }*/
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        mCameraModule.openCamera(width, height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        mCameraModule.configureTransform(width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
    }

    @Override
    public int compare(Size size, Size t1) {
        return Long.signum((long) size.getWidth() * size.getHeight() -
                (long) t1.getWidth() * t1.getHeight());
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        switch (keyEvent.getAction()) {
            case ACTION_DOWN:
                // mCamera.
                break;
            case ACTION_UP:
                break;
        }
        return false;
    }

    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }
}
