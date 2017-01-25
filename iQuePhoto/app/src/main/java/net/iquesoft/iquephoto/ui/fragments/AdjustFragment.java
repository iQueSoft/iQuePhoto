package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.adapter.AdjustAdapter;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.models.Adjust;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.presentation.presenters.fragment.AdjustPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.AdjustView;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;
import net.iquesoft.iquephoto.util.ToolbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.NONE;

public class AdjustFragment extends ToolFragment implements AdjustView {
    @InjectPresenter
    AdjustPresenter presenter;

    @BindView(R.id.adjustRecyclerView)
    RecyclerView recyclerView;

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adjust, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ImageEditorView) getActivity().findViewById(R.id.imageEditorView))
                .changeTool(NONE);
        ToolbarUtil.updateTitle(R.string.adjust, getActivity());
        ToolbarUtil.updateSubtitle(null, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupAdapter(List<Adjust> adjusts) {
        AdjustAdapter adapter = new AdjustAdapter(adjusts);
        adapter.setOnAdjustClickListener(adjust ->
                ((EditorActivity) getActivity())
                        .setupFragment(AdjustmentFragment.newInstance(adjust.getCommand()))
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }
}
