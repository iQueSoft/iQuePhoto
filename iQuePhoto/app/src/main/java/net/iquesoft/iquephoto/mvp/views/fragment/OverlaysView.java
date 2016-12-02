package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Overlay;

import java.util.List;

public interface OverlaysView extends MvpView {
    void setupAdapter(List<Overlay> overlays);
}
