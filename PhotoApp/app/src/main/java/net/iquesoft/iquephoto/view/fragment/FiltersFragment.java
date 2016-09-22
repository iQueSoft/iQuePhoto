package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.core.EditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.FiltersAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Filter;
import net.iquesoft.iquephoto.presenter.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IFiltersFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FiltersFragment extends BaseFragment implements IFiltersFragmentView {

    private boolean isHide = false;

    private Unbinder unbinder;

    private EditorView editorView;

    @Inject
    FiltersFragmentPresenterImpl presenter;

    @BindView(R.id.hideFiltersButton)
    ImageView hideFiltersButton;

    @BindView(R.id.filtersList)
    RecyclerView filtersList;

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
        presenter.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filters, container, false);
        v.setAlpha(0.8f);

        unbinder = ButterKnife.bind(this, v);

        editorView = DataHolder.getInstance().getEditorView();

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setFiltersAdapter() {
        FiltersAdapter filtersAdapter = new FiltersAdapter(Filter.getFiltersList());

        filtersAdapter.setFiltersListener(filter -> {
            if (filter.getColorMatrix() != null) {
                editorView.setFilter(filter.getColorMatrix());
            } else {
                editorView.setHasNotFiler();
            }
            //filtersAdapter.notifyDataSetChanged();
        });


        filtersList.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));
        filtersList.setAdapter(filtersAdapter);
    }

    @OnClick(R.id.hideFiltersButton)
    public void onClickHideFilters(View view) {
        if (!isHide) {
            hideFiltersButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            filtersList.setVisibility(View.GONE);
            isHide = true;
        } else {
            hideFiltersButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            filtersList.setVisibility(View.VISIBLE);
            isHide = false;
        }
    }
}
