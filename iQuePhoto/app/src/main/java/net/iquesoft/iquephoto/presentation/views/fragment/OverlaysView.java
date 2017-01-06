package net.iquesoft.iquephoto.presentation.views.fragment;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Overlay;

import java.util.List;

public interface OverlaysView extends MvpView {
    void setupAdapter(List<Overlay> overlays);

    void onOverlayChanged(Bitmap bitmap);
}
