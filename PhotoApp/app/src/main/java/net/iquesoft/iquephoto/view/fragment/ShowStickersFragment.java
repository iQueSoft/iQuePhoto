package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.core.EditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.StickersAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Sticker;
import net.iquesoft.iquephoto.model.StickersSet;
import net.iquesoft.iquephoto.presenter.ShowStickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IShowStickersFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sergey on 9/1/2016.
 */
public class ShowStickersFragment extends BaseFragment implements IShowStickersFragmentView {

    private int position;

    private Unbinder unbinder;

    @BindView(R.id.stickersRecyclerView)
    RecyclerView recyclerView;

    private EditorView editorView;

    @Inject
    ShowStickersFragmentPresenterImpl presenter;

    public static ShowStickersFragment newInstance(int position) {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new ShowStickersFragment(position);
    }

    public ShowStickersFragment(int position) {
        this.position = position;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_stickers, container, false);
        v.setAlpha(0.85f);

        unbinder = ButterKnife.bind(this, v);

        editorView = DataHolder.getInstance().getEditorView();

        StickersAdapter adapter = new StickersAdapter(getStickers(position));
        adapter.setStickersListener(sticker -> {
            editorView.addSticker(sticker);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), 4));
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private List<Sticker> getStickers(int position) {
        return StickersSet.getStickersSetsList().get(position).getStickers();
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
