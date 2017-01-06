package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.adapter.AdjustAdapter;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.NewImageEditorView;
import net.iquesoft.iquephoto.mvp.models.Adjust;
import net.iquesoft.iquephoto.mvp.presenters.fragment.AdjustPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.AdjustView;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;
import net.iquesoft.iquephoto.util.ActivityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.NONE;

public class AdjustFragment extends MvpAppCompatFragment implements AdjustView {
    @InjectPresenter
    AdjustPresenter presenter;

    @BindView(R.id.adjustRecyclerView)
    RecyclerView recyclerView;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adjust, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((NewImageEditorView) getActivity().findViewById(R.id.imageEditorView))
                .changeTool(NONE);
        ActivityUtil.updateToolbarTitle(R.string.adjust, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setupAdapter(List<Adjust> adjusts) {
        AdjustAdapter adapter = new AdjustAdapter(adjusts);
        adapter.setOnAdjustClickListener(adjust ->
                ((EditorActivity) getActivity())
                        .setupFragment(SliderControlFragment.newInstance(adjust.getCommand()))
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }
}
