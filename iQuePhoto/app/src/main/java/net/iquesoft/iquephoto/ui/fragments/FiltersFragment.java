package net.iquesoft.iquephoto.ui.fragments;

import android.graphics.ColorMatrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.FiltersAdapter;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.models.Filter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.FiltersPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.FiltersView;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;
import net.iquesoft.iquephoto.utils.ToolbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.enums.EditorTool.FILTERS;

public class FiltersFragment extends ToolFragment implements FiltersView {
    @InjectPresenter
    FiltersPresenter mPresenter;

    @ProvidePresenter
    FiltersPresenter provideFilterPresenter() {
        return new FiltersPresenter(
                getContext(), mImageEditorView.getAlteredImageBitmap()
        );
    }

    @BindView(R.id.recycler_view_filters)
    RecyclerView mFiltersRecyclerView;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    public static FiltersFragment newInstance() {
        return new FiltersFragment();
    }

    public FiltersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mImageEditorView = (ImageEditorView) getActivity().findViewById(R.id.image_editor_view);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ToolbarUtil.updateTitle(R.string.filters, getActivity());
        mImageEditorView.changeTool(FILTERS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupFiltersAdapter(Uri uri, List<Filter> filters) {
        FiltersAdapter adapter = new FiltersAdapter(uri, filters);
        adapter.setFiltersListener(
                new FiltersAdapter.OnFilterClickListener() {
                    @Override
                    public void onFilterClicked(Filter filter) {
                        mPresenter.changeFilter(filter);
                    }

                    @Override
                    public void onIntensityClicked() {
                        // TODO: Filter intensity.
                        //mPresenter.changeFilterIntensity(mImageEditorView.get);
                    }
                }
        );

        mFiltersRecyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));
        mFiltersRecyclerView.setAdapter(adapter);
    }

    @Override
    public void filterChanged(ColorMatrix colorMatrix) {
        mImageEditorView.setFilter(colorMatrix);
    }

    @Override
    public void onChangeFilterIntensityClicked(Fragment fragment) {
        ((EditorActivity) getActivity()).setupFragment(fragment);
    }
}
