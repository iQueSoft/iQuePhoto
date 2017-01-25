package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.FontsAdapter;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.models.Font;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.presentation.presenters.fragment.FontsPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.FontsView;
import net.iquesoft.iquephoto.util.ToolbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.TEXT;

public class FontsFragment extends ToolFragment implements FontsView {
    @InjectPresenter
    FontsPresenter presenter;

    @BindView(R.id.fontsRecyclerView)
    RecyclerView recyclerView;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    public static FontsFragment newInstance() {
        return new FontsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView = (ImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fonts, container, false);

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
        mImageEditorView.changeTool(TEXT);
        ToolbarUtil.updateSubtitle(R.string.font, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_apply) {
            Toast.makeText(getContext(), "Da", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupAdapter(List<Font> fonts) {
        FontsAdapter adapter = new FontsAdapter(fonts);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerView.setAdapter(adapter);
    }
}