package net.iquesoft.iquephoto.ui.fragments;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.camera.Camera2Module;
import net.iquesoft.iquephoto.core.camera.Camera2View;
import net.iquesoft.iquephoto.mvp.presenters.fragment.Camera2Presenter;
import net.iquesoft.iquephoto.mvp.views.fragment.Camera2FragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Fragment extends MvpAppCompatFragment implements Camera2FragmentView {
    @InjectPresenter
    Camera2Presenter presenter;

    @BindView(R.id.camera2View)
    Camera2View camera2View;

    private Context mContext;

    private Unbinder mUnbinder;

    private Camera2Module mCameraModule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_2, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        Log.i(Camera2Fragment.class.getSimpleName(), "USED CAMERA 2");

        mContext = view.getContext();

        mCameraModule = new Camera2Module(getActivity(), camera2View);
        //camera2View.setAspectRatio(0, 0);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCameraModule.startHandlerThread();

        camera2View.setCameraModule(mCameraModule);
    }

    @Override
    public void onPause() {
        mCameraModule.closeCamera();
        mCameraModule.stopHandlerThread();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

    }

    @Override
    public void takePhoto() {
        mCameraModule.takePicture();
    }

    public void setFilter(ColorMatrix colorMatrix) {
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        camera2View.setLayerPaint(paint);
    }
}