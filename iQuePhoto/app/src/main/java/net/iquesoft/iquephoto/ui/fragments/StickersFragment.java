package net.iquesoft.iquephoto.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.StickersAdapter;
import net.iquesoft.iquephoto.core.editor.NewImageEditorView;
import net.iquesoft.iquephoto.models.Sticker;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.presentation.presenters.fragment.StickersPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.StickersView;
import net.iquesoft.iquephoto.util.ToolbarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StickersFragment extends ToolFragment implements StickersView {
    public static final String ARG_TITLE = "stickers_title";
    public static final String ARG_STICKERS = "stickers";

    @InjectPresenter
    StickersPresenter mPresenter;

    @ProvidePresenter
    StickersPresenter provideStickersPresenter() {
        return new StickersPresenter(getArguments());
    }

    @BindView(R.id.stickersRecyclerView)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    
    public static StickersFragment newInstance(@StringRes int title, ArrayList<Sticker> stickers) {
        StickersFragment fragment = new StickersFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
        args.putParcelableArrayList(ARG_STICKERS, stickers);

        fragment.setArguments(args);

        return fragment;
    }

    public StickersFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_stickers, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupAdapter(List<Sticker> stickers) {
        StickersAdapter adapter = new StickersAdapter(stickers);
        adapter.setOnStickerClickListener(sticker ->
                mPresenter.stickerClicked(getContext(), sticker)
        );

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setupToolbarSubtitle(@StringRes int subtitle) {
        ToolbarUtil.updateSubtitle(subtitle, getActivity());
    }

    @Override
    public void addSticker(Bitmap bitmap) {
        ((NewImageEditorView) getActivity()
                .findViewById(R.id.imageEditorView)).addSticker(bitmap);
    }
}
