package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.adapters.AdjustAdapter;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Adjust;
import net.iquesoft.iquephoto.presenter.AdjustmentFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IAdjustmentFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AdjustmentFragment extends BaseFragment implements IAdjustmentFragmentView {

    private int mAdjustPosition;

    private List<Adjust> mAdjustList = Adjust.getAdjustList();

    private Adjust mCurrentAdjust;

    private Unbinder mUnbinder;

    private AdjustAdapter mAdapter;

    private ImageEditorView mImageEditorView;

    @BindView(R.id.adjustRecyclerView)
    RecyclerView recyclerView;

    @Inject
    AdjustmentFragmentPresenterImpl presenter;

    public static AdjustmentFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new AdjustmentFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_adjustment, container, false);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        mUnbinder = ButterKnife.bind(this, v);

        mAdapter = new AdjustAdapter(mAdjustList);

        mAdapter.setAdjustListener((adjust, position) -> {
            mAdjustPosition = position;
            mCurrentAdjust = adjust;
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));

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
