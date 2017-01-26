package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ColorAdapter;
import net.iquesoft.iquephoto.adapters.SizesAdapter;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.models.BrushSize;
import net.iquesoft.iquephoto.models.EditorColor;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.presentation.presenters.fragment.DrawingPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.DrawingView;
import net.iquesoft.iquephoto.utils.ToolbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.DRAWING;

public class DrawingFragment extends ToolFragment implements DrawingView {
    @InjectPresenter
    DrawingPresenter mPresenter;

    @BindView(R.id.colorsRecyclerView)
    RecyclerView mColorsRecyclerView;

    @BindView(R.id.sizesRecyclerView)
    RecyclerView mSizesRecyclerView;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    public static DrawingFragment newInstance() {
        return new DrawingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView = (ImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawing, container, false);

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
        mImageEditorView.changeTool(DRAWING);
        ToolbarUtil.updateTitle(R.string.drawing, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupSizesAdapter(List<BrushSize> sizes) {
        SizesAdapter adapter = new SizesAdapter(sizes);
        adapter.setOnSizeClickListener(size ->
                mImageEditorView.setBrushSize(size.getSize())
        );
        mSizesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        mSizesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setupColorsAdapter(List<EditorColor> colors) {
        ColorAdapter adapter = new ColorAdapter(colors);
        adapter.setOnColorClickListener(editorColor ->
                mPresenter.changeBrushColor(getContext(), editorColor)
        );
        mColorsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        mColorsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBrushColorChanged(@ColorInt int color) {
        mImageEditorView.setBrushColor(color);
    }
}