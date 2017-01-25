package net.iquesoft.iquephoto.ui.fragments;

import android.graphics.ColorMatrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.FiltersAdapter;
import net.iquesoft.iquephoto.core.editor.NewImageEditorView;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.models.Filter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.FiltersPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.FiltersView;
import net.iquesoft.iquephoto.util.BitmapUtil;
import net.iquesoft.iquephoto.util.ToolbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.FILTERS;

public class FiltersFragment extends ToolFragment implements FiltersView {
    @InjectPresenter
    FiltersPresenter mPresenter;

    @ProvidePresenter
    FiltersPresenter provideFilterPresenter() {
        return new FiltersPresenter(
                getContext(), mImageEditorView.getAlteredImageBitmap()
        );
    }

    @BindView(R.id.filtersRecyclerView)
    RecyclerView filtersList;

    private Unbinder mUnbinder;

    private NewImageEditorView mImageEditorView;

    public static FiltersFragment newInstance() {
        return new FiltersFragment();
    }

    public FiltersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mImageEditorView = (NewImageEditorView) getActivity().findViewById(R.id.imageEditorView);

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
        adapter.setFiltersListener(filter ->
                mPresenter.changeFilter(filter)
        );

        filtersList.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));
        filtersList.setAdapter(adapter);
    }

    @Override
    public void filterChanged(ColorMatrix colorMatrix) {
        mImageEditorView.setFilter(colorMatrix);
    }
}
