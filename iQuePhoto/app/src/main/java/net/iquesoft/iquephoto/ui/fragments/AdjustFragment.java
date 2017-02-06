package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.adapters.AdjustAdapter;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.models.Adjust;
import net.iquesoft.iquephoto.presentation.presenters.fragment.AdjustPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.AdjustView;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;
import net.iquesoft.iquephoto.utils.ToolbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.enums.EditorTool.NONE;

public class AdjustFragment extends MvpAppCompatFragment implements AdjustView {
    @InjectPresenter
    AdjustPresenter mPresenter;

    @BindView(R.id.recycler_view_adjust)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        ((ImageEditorView) getActivity().findViewById(R.id.image_editor_view))
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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.removeItem(R.id.action_share);
    }

    @Override
    public void setupAdapter(List<Adjust> adjusts) {
        AdjustAdapter adapter = new AdjustAdapter(adjusts);
        adapter.setOnAdjustClickListener(adjust -> mPresenter.changeAdjust(adjust));
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false)
        );
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void adjustChanged(Fragment fragment) {
        ((EditorActivity) getActivity())
                .setupFragment(fragment);
    }
}