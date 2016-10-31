package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ToolsAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.presenter.ToolsFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IEditorActivityView;
import net.iquesoft.iquephoto.view.IToolsFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ToolsFragment extends BaseFragment implements IToolsFragmentView {

    private Unbinder mUnbinder;

    private Tool mCurrentTool;

    private ToolsAdapter mAdapter;

    @BindView(R.id.toolsRecyclerView)
    RecyclerView recyclerView;

    private ImageEditorView mImageEditorView;

    @Inject
    IEditorActivityView view;

    @Inject
    ToolsFragmentPresenterImpl presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tools, container, false);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        mUnbinder = ButterKnife.bind(this, v);

        mAdapter = new ToolsAdapter(Tool.getToolsList());

        mAdapter.setToolsListener(tool -> {
            if (tool != mCurrentTool) {
                try {
                    view.setupFragment(tool.getFragment());

                    mImageEditorView.setCommand(tool.getTitle());

                } catch (NullPointerException e) {
                    e.printStackTrace();

                    mImageEditorView.setCommand(0);
                }
            } else {
                mCurrentTool = null;
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
