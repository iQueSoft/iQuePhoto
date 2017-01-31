package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.models.Filter;
import net.iquesoft.iquephoto.models.ParcelablePaint;
import net.iquesoft.iquephoto.presentation.views.fragment.FiltersView;
import net.iquesoft.iquephoto.ui.fragments.TransparencyFragment;
import net.iquesoft.iquephoto.utils.BitmapUtil;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class FiltersPresenter extends MvpPresenter<FiltersView> {
    @Inject
    List<Filter> mFilters;

    public FiltersPresenter(@NonNull Context context, @NonNull Bitmap bitmap) {
        App.getAppComponent().inject(this);

        Uri uri = BitmapUtil.getUriOfBitmap(context, bitmap);

        getViewState().setupFiltersAdapter(uri, mFilters);
    }

    public void changeFilter(@NonNull Filter filter) {
        getViewState().filterChanged(filter.getColorMatrix());
    }

    public void changeFilterIntensity(@NonNull Paint filterPaint) {
        getViewState().onChangeFilterIntensityClicked(
                TransparencyFragment.newInstance(filterPaint)
        );
    }
}