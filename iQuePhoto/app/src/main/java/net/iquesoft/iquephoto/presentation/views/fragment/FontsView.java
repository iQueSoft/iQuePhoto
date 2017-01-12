package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Font;

import java.util.List;

public interface FontsView extends MvpView {
    void setupAdapter(List<Font> fonts);
}
