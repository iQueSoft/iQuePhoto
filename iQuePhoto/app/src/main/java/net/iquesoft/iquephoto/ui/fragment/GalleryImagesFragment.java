package net.iquesoft.iquephoto.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.ImageAlbumsAdapter;
import net.iquesoft.iquephoto.adapter.ImagesAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IGalleryActivityComponent;
import net.iquesoft.iquephoto.model.Image;
import net.iquesoft.iquephoto.presentation.presenter.fragment.GalleryImagesPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.fragment.GalleryImagesView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GalleryImagesFragment extends BaseFragment implements GalleryImagesView {
    private Unbinder mUnbinder;

    @Inject
    GalleryImagesPresenterImpl presenter;

    @BindView(R.id.imagesRecyclerView)
    RecyclerView recyclerView;

    private ImagesAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(IGalleryActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_images, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupAdapter(List<Image> images) {
        mAdapter = new ImagesAdapter(images);

        mAdapter.setOnImageClickListener(image -> {

        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(mAdapter);

    }
}
