package net.iquesoft.iquephoto.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.ImagesAdapter;
import net.iquesoft.iquephoto.models.Image;
import net.iquesoft.iquephoto.presentation.presenters.fragment.GalleryImagesPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.StickersPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.GalleryImagesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GalleryImagesFragment extends MvpAppCompatFragment implements GalleryImagesView {
    public static final String ARG_PARAM = "images";

    @InjectPresenter
    GalleryImagesPresenter mPresenter;

    @ProvidePresenter
    GalleryImagesPresenter provideGalleryImagesPresenter() {
        return new GalleryImagesPresenter(getArguments());
    }

    @BindView(R.id.imagesRecyclerView)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;

    public static GalleryImagesFragment newInstance(ArrayList<Image> images) {
        GalleryImagesFragment fragment = new GalleryImagesFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, images);

        fragment.setArguments(args);

        return fragment;
    }

    public GalleryImagesFragment() {
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
    public void setupAdapter(List<Image> images) {
        ImagesAdapter adapter = new ImagesAdapter(images);

        adapter.setOnImageClickListener(image -> {
            mPresenter.setImageForEdit(image);
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void editImage(String imagePath) {
        Intent intent = new Intent("app.intent.action.Preview");
        intent.putExtra("Image", imagePath);
        startActivity(intent);
        getActivity().finish();
    }
}
