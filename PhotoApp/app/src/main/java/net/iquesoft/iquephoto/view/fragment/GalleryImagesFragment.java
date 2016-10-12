package net.iquesoft.iquephoto.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.GalleryAdapter;
import net.iquesoft.iquephoto.model.ImageGallery;
import net.iquesoft.iquephoto.presenter.GalleryImagesPresenterImpl;
import net.iquesoft.iquephoto.utils.GalleryImageLoader;
import net.iquesoft.iquephoto.view.IGalleryImagesFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GalleryImagesFragment extends BottomSheetDialogFragment implements IGalleryImagesFragmentView {

    @BindView(R.id.gallryImagesList)
    RecyclerView recyclerView;

    private GalleryImageLoader mGalleryImageLoader;

    private GalleryAdapter mAdapter;

    @Inject
    GalleryImagesPresenterImpl presenter;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN)
                dismiss();
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.fragment_gallery_image, null);
        dialog.setContentView(view);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetCallback);
//            if (builder.peekHeight > 0) {
//                // ((BottomSheetBehavior) behavior).setPeekHeight(1500);
//                ((BottomSheetBehavior) behavior).setPeekHeight(builder.peekHeight);
//            }

        }

        mGalleryImageLoader = new GalleryImageLoader(getContext());
        mGalleryImageLoader.execute();

        mGalleryImageLoader.setListener(imageGalleryList -> {
            mAdapter = new GalleryAdapter(imageGalleryList);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
            recyclerView.setAdapter(mAdapter);
        });

        ButterKnife.bind(this, view);
    }

    @Override
    public void setupAdapter(List<ImageGallery> imagesList) {

    }

    @OnClick(R.id.closeGalleryButton)
    void onClickClose() {
        dismiss();
    }

}
