package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.models.Sticker;
import net.iquesoft.iquephoto.models.StickersSet;
import net.iquesoft.iquephoto.presentation.views.fragment.StickersView;
import net.iquesoft.iquephoto.ui.fragments.StickersFragment;
import net.iquesoft.iquephoto.utils.BitmapUtil;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class StickersPresenter extends MvpPresenter<StickersView> {
    @Inject
    List<StickersSet> stickersSets;

    public StickersPresenter(@NonNull Bundle bundle) {
        setupStickersSet(bundle);
        App.getAppComponent().inject(this);
    }

    private void setupStickersSet(Bundle bundle) {
        List<Sticker> stickers = bundle.getParcelableArrayList(StickersFragment.ARG_STICKERS);

        getViewState().setupAdapter(stickers);
        getViewState().setupToolbarSubtitle(bundle.getInt(StickersFragment.ARG_TITLE));
    }

    public void stickerClicked(@NonNull Context context, Sticker sticker) {
        Bitmap bitmap = BitmapUtil.drawable2Bitmap(context, sticker.getImage());

        getViewState().addSticker(bitmap);
    }
}
