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
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.FiltersAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Filter;
import net.iquesoft.iquephoto.presenter.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IFiltersFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FiltersFragment extends BaseFragment implements IFiltersFragmentView {

    private boolean mIsHide;
    private boolean mIsSeekBarHide;

    private int intensity;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    private Filter mCurrentFilter;

    @Inject
    FiltersFragmentPresenterImpl presenter;

    @BindView(R.id.filterSeekBar)
    DiscreteSeekBar seekBar;

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
        //v.setAlpha(0.8f);

        mUnbinder = ButterKnife.bind(this, v);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                mImageEditorView.setFilterIntensity(value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

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

    @Override
    public void setFiltersAdapter() {
        FiltersAdapter filtersAdapter = new FiltersAdapter(Filter.getFiltersList(), DataHolder.getInstance().getBitmap());

        filtersAdapter.setFiltersListener(filter -> {
            if (mCurrentFilter == filter) {
                if (!mIsSeekBarHide)
                    seekBar.setVisibility(View.VISIBLE);
                else {
                    seekBar.setVisibility(View.GONE);
                }
            } else {
                seekBar.setVisibility(View.GONE);
                seekBar.setProgress(100);
                mImageEditorView.setFilterIntensity(100);
            }

            mImageEditorView.setFilter(filter.getColorMatrix());
            mCurrentFilter = filter;
        });


        filtersList.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));
        filtersList.setAdapter(filtersAdapter);
    }

    @OnClick(R.id.hideFiltersButton)
    public void onClickHideFilters(View view) {
        if (!mIsHide) {
            hideFiltersButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            filtersList.setVisibility(View.GONE);
            mIsHide = true;
        } else {
            hideFiltersButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            filtersList.setVisibility(View.VISIBLE);
            mIsHide = false;
        }
    }
}
