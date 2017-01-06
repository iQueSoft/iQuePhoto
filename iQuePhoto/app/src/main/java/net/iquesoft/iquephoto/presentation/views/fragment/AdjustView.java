package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Adjust;

import java.util.List;

public interface AdjustView extends MvpView {
    void setupAdapter(List<Adjust> adjusts);
}
