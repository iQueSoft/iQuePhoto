package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Sticker;

import java.util.List;

public interface StickersView extends MvpView {
    void setupAdapter(List<Sticker> stickers);
}
