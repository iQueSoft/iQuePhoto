package net.iquesoft.iquephoto.presentation.views.fragment;

import android.graphics.ColorMatrix;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Filter;

import java.util.List;

public interface FiltersView extends MvpView {
    void setupFiltersAdapter(Uri uri, List<Filter> filters);

    void filterChanged(ColorMatrix colorMatrix);

    void onChangeFilterIntensityClicked(Fragment fragment);
}