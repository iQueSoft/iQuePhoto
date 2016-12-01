package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.FiltersAdapter;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.mvp.models.Filter;
import net.iquesoft.iquephoto.mvp.presenters.fragment.FiltersPresenter;
import net.iquesoft.iquephoto.mvp.views.activity.EditorView;
import net.iquesoft.iquephoto.mvp.views.fragment.FiltersView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FiltersFragment extends MvpAppCompatFragment implements FiltersView {
    @InjectPresenter
    FiltersPresenter presenter;

    @BindView(R.id.filterSeekBar)
    DiscreteSeekBar seekBar;

    @BindView(R.id.filtersList)
    RecyclerView filtersList;

    private Unbinder mUnbinder;

    public FiltersFragment() {
    }

    public static FiltersFragment newInstance() {
        return new FiltersFragment();
    }

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
        View v = inflater.inflate(R.layout.fragment_filters, container, false);

        mUnbinder = ButterKnife.bind(this, v);

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                // TODO: mImageEditorView.setFilterIntensity(value);
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
            // TODO: mImageEditorView.setFilter(filter.getColorMatrix());
        });

        filtersList.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));
        filtersList.setAdapter(adapter);
    }

    @OnClick(R.id.filterBack)
    void onClickBack() {
        // TODO: editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.filterApply)
    void onClickApply() {
        /* TODO: editorActivityView.getImageEditorView().apply(EditorCommand.FILTERS);
        editorActivityView.navigateBack(true);*/
    }
}
