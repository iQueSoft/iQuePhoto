package net.iquesoft.iquephoto.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.adapter.AdjustAdapter;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Adjust;
import net.iquesoft.iquephoto.presentation.presenter.fragment.AdjustPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.SliderControlFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.AdjustView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AdjustFragment extends BaseFragment implements AdjustView {

    private List<Adjust> mAdjustList = Adjust.getAdjustList();

    private Unbinder mUnbinder;

    private AdjustAdapter mAdapter;

    @BindView(R.id.adjustRecyclerView)
    RecyclerView recyclerView;

    @Inject
    EditorView editorActivityView;

    @Inject
    AdjustPresenterImpl presenter;

    @Inject
    SliderControlFragmentPresenterImpl sliderControlFragmentPresenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adjust, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mAdapter = new AdjustAdapter(mAdjustList);

        mAdapter.setOnAdjustClickListener(adjust -> {
            sliderControlFragmentPresenter.setCommand(adjust.getCommand());
            //editorActivityView.setupFragment(SliderControlFragment.newInstance(adjust.getCommand()));
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));

        recyclerView.setAdapter(mAdapter);

        return view;
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

    @OnClick(R.id.adjustBack)
    public void onClickBack() {
        editorActivityView.navigateBack(true);
    }
}
