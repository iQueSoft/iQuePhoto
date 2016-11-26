package net.iquesoft.iquephoto.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.StickersAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.di.components.EditorComponent;
import net.iquesoft.iquephoto.model.Sticker;
import net.iquesoft.iquephoto.model.StickersSet;
import net.iquesoft.iquephoto.presentation.presenter.fragment.ShowStickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.fragment.ShowStickersView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class StickersFragment extends BaseFragment implements ShowStickersView {

    private int mPosition;

    private Unbinder mUnbinder;

    @BindView(R.id.stickersRecyclerView)
    RecyclerView recyclerView;

    private ImageEditorView mImageEditorView;

    @Inject
    ShowStickersFragmentPresenterImpl presenter;

    public static StickersFragment newInstance(int position) {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new StickersFragment(position);
    }

    public StickersFragment(int position) {
        mPosition = position;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(EditorComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_stickers, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        StickersAdapter adapter = new StickersAdapter(getStickers(mPosition));
        adapter.setOnStickerClickListener(sticker -> {
            //editorView.addSticker(sticker);
            mImageEditorView.addSticker(sticker);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private List<Sticker> getStickers(int position) {
        return StickersSet.getStickersSetsList().get(position).getStickers();
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
