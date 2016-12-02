package net.iquesoft.iquephoto.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.StickersAdapter;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.mvp.models.Sticker;
import net.iquesoft.iquephoto.mvp.presenters.fragment.ShowStickersFragmentPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.ShowStickersView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StickersFragment extends MvpAppCompatFragment implements ShowStickersView {
    private static final String ARG_PARAM = "position";

    @InjectPresenter
    ShowStickersFragmentPresenter presenter;

    @BindView(R.id.stickersRecyclerView)
    RecyclerView recyclerView;
    
    private Context mContext;

    private Unbinder mUnbinder;

    public static StickersFragment newInstance(int position) {
        StickersFragment fragment = new StickersFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, position);

        fragment.setArguments(args);

        return fragment;
    }

    public StickersFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            presenter.setupStickersSet(getArguments().getInt(ARG_PARAM));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_stickers, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mContext = view.getContext();

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
        adapter.setOnStickerClickListener(sticker -> {
            ((ImageEditorView) getActivity()
                    .findViewById(R.id.editorImageView)).addSticker(sticker);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setAdapter(adapter);
    }
}
