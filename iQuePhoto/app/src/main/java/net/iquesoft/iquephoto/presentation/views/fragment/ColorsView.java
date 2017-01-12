package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.EditorColor;

import java.util.List;

public interface ColorsView extends MvpView {
    void setupAdapter(List<EditorColor> colors);
}
