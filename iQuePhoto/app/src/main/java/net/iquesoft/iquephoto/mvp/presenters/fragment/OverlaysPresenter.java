package net.iquesoft.iquephoto.mvp.presenters.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.mvp.models.Overlay;
import net.iquesoft.iquephoto.mvp.views.fragment.OverlaysView;
import net.iquesoft.iquephoto.util.BitmapUtil;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class OverlaysPresenter extends MvpPresenter<OverlaysView> {
    @Inject
    List<Overlay> mOverlays;

    public OverlaysPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupAdapter(mOverlays);
    }

    public void changeOverlay(@NonNull Context context, Overlay overlay) {
        Bitmap bitmap = BitmapUtil.drawable2Bitmap(context, overlay.getImage());

        getViewState().onOverlayChanged(bitmap);
    }
}
