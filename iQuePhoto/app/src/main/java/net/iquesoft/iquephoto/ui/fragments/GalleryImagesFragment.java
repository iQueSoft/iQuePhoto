package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.ImagesAdapter;
import net.iquesoft.iquephoto.mvp.models.Image;
import net.iquesoft.iquephoto.mvp.presenters.fragment.GalleryImagesPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.GalleryImagesView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GalleryImagesFragment extends MvpAppCompatFragment implements GalleryImagesView {
    private Unbinder mUnbinder;

    private List<Image> mImages;

    @InjectPresenter
    GalleryImagesPresenter presenter;

    @BindView(R.id.imagesRecyclerView)
    RecyclerView recyclerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupAdapter() {
        ImagesAdapter adapter = new ImagesAdapter(mImages);

        adapter.setOnImageClickListener(image -> {
            presenter.setImageForEditing(image.getPath());
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
    }

    public void setImages(List<Image> images) {
        mImages = images;
    }
}
