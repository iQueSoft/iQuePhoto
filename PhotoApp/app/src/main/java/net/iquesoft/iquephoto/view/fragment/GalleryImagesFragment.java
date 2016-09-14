package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.model.GalleryImage;
import net.iquesoft.iquephoto.presenter.GalleryImagesPresenterImpl;
import net.iquesoft.iquephoto.view.IGalleryImagesFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GalleryImagesFragment extends BaseFragment implements IGalleryImagesFragmentView {

    @BindView(R.id.gallryImagesList)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    @Inject
    GalleryImagesPresenterImpl presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery_image, container, false);

        unbinder = ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setupAdapter(List<GalleryImage> imagesList) {

    }
}
