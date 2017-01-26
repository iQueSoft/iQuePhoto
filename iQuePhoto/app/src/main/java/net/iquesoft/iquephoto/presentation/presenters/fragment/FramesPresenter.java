package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.models.Frame;
import net.iquesoft.iquephoto.presentation.views.fragment.FramesView;
import net.iquesoft.iquephoto.utils.BitmapUtil;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class FramesPresenter extends MvpPresenter<FramesView> {
    @Inject
    List<Frame> mFrames;

    public FramesPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupAdapter(mFrames);
    }

    public void changeOverlay(@NonNull Context context, Frame frame) {
        Bitmap bitmap = BitmapUtil.drawable2Bitmap(context, frame.getImage());

        getViewState().onFrameChanged(bitmap);
    }
}
