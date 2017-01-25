package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.models.Filter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.CameraFiltersPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.CameraFiltersView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CameraFiltersFragment extends MvpAppCompatFragment implements CameraFiltersView {

    private Unbinder mUnbinder;

    @InjectPresenter
    CameraFiltersPresenter presenter;

    @BindView(R.id.cameraFiltersRecyclerView)
    RecyclerView recyclerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_filters, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
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

    @Override
    public void setupFiltersAdapter(List<Filter> filters) {
        /*FiltersAdapter adapter = new FiltersAdapter(filters);
        adapter.setFiltersListener(filter ->
                mPresenter.onFilterClick(filter)
        );

        recyclerView.setLayoutManager(new
                LinearLayoutManager(null, LinearLayout.HORIZONTAL, false)
        );
        recyclerView.setAdapter(adapter);*/
    }
}
