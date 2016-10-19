package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.FramesAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Frame;
import net.iquesoft.iquephoto.presenter.FramesFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IFrameFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FrameFragment extends BaseFragment implements IFrameFragmentView {

    private boolean mIsHide;

    private Unbinder mUnbinder;

    private List<Frame> mFrameList = Frame.getFramesList();

    private FramesAdapter mAdapter;

    private ImageEditorView mImageEditorView;
    
    @Inject
    FramesFragmentPresenterImpl presenter;

    @BindView(R.id.hideFrameButton)
    ImageView hideButton;

    @BindView(R.id.frameRecyclerView)
    RecyclerView recyclerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frames, container, false);
        //v.setAlpha(0.8f);

        mUnbinder = ButterKnife.bind(this, v);

        mAdapter = new FramesAdapter(mFrameList);
        mAdapter.setFramesListener(frame -> {
            mImageEditorView.setFrame(getResources().getDrawable(frame.getImage()));
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.hideFrameButton)
    void onClickHide() {
        if (!mIsHide) {
            hideButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            recyclerView.setVisibility(View.GONE);
            mIsHide = true;
        } else {
            hideButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            recyclerView.setVisibility(View.VISIBLE);
            mIsHide = false;
        }
    }
}
