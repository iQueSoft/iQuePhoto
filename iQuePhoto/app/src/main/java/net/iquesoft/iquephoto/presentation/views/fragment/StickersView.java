package net.iquesoft.iquephoto.presentation.views.fragment;

import android.graphics.Bitmap;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Sticker;

import java.util.List;

public interface StickersView extends MvpView {
    void setupAdapter(List<Sticker> stickers);

    void setupToolbarSubtitle(@StringRes int subtitle);

    void addSticker(Bitmap bitmap);
}
