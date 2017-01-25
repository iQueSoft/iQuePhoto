package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.ColorAdapter;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.models.EditorColor;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.presentation.presenters.fragment.ColorsPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.ColorsView;
import net.iquesoft.iquephoto.util.ToolbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.TEXT;

public class ColorsFragment extends ToolFragment implements ColorsView {
    @InjectPresenter
    ColorsPresenter presenter;

    @BindView(R.id.colorsRecyclerView)
    RecyclerView recyclerView;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    public static ColorsFragment newInstance() {
        return new ColorsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView = (ImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_colors, container, false);

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
        ToolbarUtil.updateSubtitle(R.string.color, getActivity());
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
    public void setupAdapter(List<EditorColor> colors) {
        ColorAdapter adapter = new ColorAdapter(colors);
        recyclerView.setLayoutManager(
                new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerView.setAdapter(adapter);
    }
}