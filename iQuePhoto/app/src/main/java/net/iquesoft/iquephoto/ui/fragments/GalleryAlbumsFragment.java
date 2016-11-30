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
import net.iquesoft.iquephoto.adapter.ImageAlbumsAdapter;
import net.iquesoft.iquephoto.mvp.models.ImageAlbum;
import net.iquesoft.iquephoto.mvp.presenters.fragment.GalleryAlbumsPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.GalleryAlbumsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GalleryAlbumsFragment extends MvpAppCompatFragment implements GalleryAlbumsView {
    @InjectPresenter
    GalleryAlbumsPresenter presenter;

    @BindView(R.id.albumsRecyclerView)
    RecyclerView recyclerView;

    private Unbinder mUnbinder;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_albums, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.fetchImages(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupAdapter(List<ImageAlbum> imageAlbums) {
        ImageAlbumsAdapter adapter = new ImageAlbumsAdapter(imageAlbums);

        adapter.setOnAlbumClickListener(imageAlbum -> {
            presenter.showAlbumImages(imageAlbum);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
    }
}
