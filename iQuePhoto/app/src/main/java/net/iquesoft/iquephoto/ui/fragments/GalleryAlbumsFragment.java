package net.iquesoft.iquephoto.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ImageAlbumsAdapter;
import net.iquesoft.iquephoto.models.ImageAlbum;
import net.iquesoft.iquephoto.presentation.presenters.fragment.GalleryAlbumsPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.GalleryAlbumsView;
import net.iquesoft.iquephoto.ui.activities.GalleryActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GalleryAlbumsFragment extends MvpAppCompatFragment implements GalleryAlbumsView {
    @InjectPresenter
    GalleryAlbumsPresenter mPresenter;

    @BindView(R.id.recycler_view_albums)
    RecyclerView mRecyclerView;

    @BindView(R.id.linear_layout_no_images)
    LinearLayout mNoImagesLinearLayout;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_albums, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.fetchImages(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void showNoImages() {
        mNoImagesLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setupAdapter(List<ImageAlbum> imageAlbums) {
        if (mNoImagesLinearLayout.getVisibility() == View.VISIBLE) {
            mNoImagesLinearLayout.setVisibility(View.GONE);
        }

        ImageAlbumsAdapter adapter = new ImageAlbumsAdapter(imageAlbums);
        adapter.setOnAlbumClickListener(imageAlbum ->
                ((GalleryActivity) getActivity()).showImages(imageAlbum));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.button_take_photo)
    void onClickTakePhoto() {
        Intent intent = new Intent("app.intent.action.Camera");
        startActivity(intent);
    }
}