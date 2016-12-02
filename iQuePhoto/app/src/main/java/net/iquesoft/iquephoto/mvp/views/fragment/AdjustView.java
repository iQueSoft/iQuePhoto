package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Adjust;

import java.util.List;

public interface AdjustView extends MvpView {
    void setupAdapter(List<Adjust> adjusts);
}
