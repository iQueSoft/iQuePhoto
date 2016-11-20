package net.iquesoft.iquephoto.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.FiltersAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Filter;
import net.iquesoft.iquephoto.presentation.presenter.fragment.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.FiltersView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FiltersFragment extends BaseFragment implements FiltersView {

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    @Inject
    EditorView editorActivityView;

    @Inject
    FiltersFragmentPresenterImpl presenter;

    @BindView(R.id.filterSeekBar)
    DiscreteSeekBar seekBar;

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
        FiltersAdapter adapter = new FiltersAdapter(Filter.getFiltersList());

        adapter.setFiltersListener(filter -> {
            mImageEditorView.setFilter(filter.getColorMatrix());
        });

        filtersList.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));
        filtersList.setAdapter(adapter);
    }

    @OnClick(R.id.filterBack)
    void onClickBack() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.filterApply)
    void onClickApply() {
        editorActivityView.getImageEditorView().apply(EditorCommand.FILTERS);
        editorActivityView.navigateBack(true);
    }
}
