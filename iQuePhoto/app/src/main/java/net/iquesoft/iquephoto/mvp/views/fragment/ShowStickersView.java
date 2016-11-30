package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Sticker;

import java.util.List;

public interface ShowStickersView extends MvpView {
    void setupAdapter(List<Sticker> stickers);
}
