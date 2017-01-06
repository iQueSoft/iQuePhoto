package net.iquesoft.iquephoto.presentation.views.fragment;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Frame;

import java.util.List;

public interface FramesView extends MvpView {
    void setupAdapter(List<Frame> frames);

    void onFrameChanged(Bitmap bitmap);
}
