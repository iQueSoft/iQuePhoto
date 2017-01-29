package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ToolsAdapter;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.models.Tool;
import net.iquesoft.iquephoto.presentation.presenters.fragment.ToolsPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.ToolsView;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.NONE;

public class ToolsFragment extends MvpAppCompatFragment implements ToolsView {
    @InjectPresenter
    ToolsPresenter mPresenter;

    @BindView(R.id.toolsRecyclerView)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageEditorView imageEditorView =
                (ImageEditorView) getActivity().findViewById(R.id.imageEditorView);
        imageEditorView.changeTool(NONE);

        Button undoButton = (Button) getActivity().findViewById(R.id.undoButton);
        
        if (imageEditorView.hasChanges()) {
            undoButton.setVisibility(View.VISIBLE);
        } else {
            undoButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupTools(List<Tool> tools) {
        ToolsAdapter adapter = new ToolsAdapter(tools);
        adapter.setOnToolsClickListener(tool ->
                ((EditorActivity) getActivity()).setupFragment(tool.getFragment())
        );

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        mRecyclerView.setAdapter(adapter);
    }
}