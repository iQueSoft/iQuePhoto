package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Frame;

import java.util.List;

public interface FramesView extends MvpView {
    void setupAdapter(List<Frame> frames);
}
