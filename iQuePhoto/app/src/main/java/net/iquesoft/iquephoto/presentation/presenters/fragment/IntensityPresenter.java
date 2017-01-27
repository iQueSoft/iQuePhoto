package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.models.Image;
import net.iquesoft.iquephoto.presentation.views.fragment.IntensityView;
import net.iquesoft.iquephoto.ui.fragments.GalleryImagesFragment;
import net.iquesoft.iquephoto.ui.fragments.IntensityFragment;

@InjectViewState
public class IntensityPresenter extends MvpPresenter<IntensityView> {
    private Paint mPaint;

    public IntensityPresenter(@NonNull Bundle bundle) {
        setupAlbumImages(bundle);
    }

    private void setupAlbumImages(Bundle bundle) {
        bundle.getParcelable(IntensityFragment.ARG_PARAM);

    }

    public void progressChanged(int value) {

    }
}