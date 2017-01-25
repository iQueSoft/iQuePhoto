package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ColorAdapter;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.models.EditorColor;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.presentation.presenters.fragment.DrawingPresenter;
import net.iquesoft.iquephoto.ui.dialogs.ColorPickerDialog;
import net.iquesoft.iquephoto.presentation.views.fragment.DrawingView;
import net.iquesoft.iquephoto.util.ToolbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.DRAWING;

public class DrawingFragment extends ToolFragment implements DrawingView {
    @InjectPresenter
    DrawingPresenter mPresenter;

    @BindView(R.id.drawingSettings)
    LinearLayout drawingSettings;

    @BindView(R.id.colorsRecyclerView)
    RecyclerView mRecyclerView;

    /*@BindView(R.id.brashSizeSeekBar)
    DiscreteSeekBar brashSizeSeekBar;*/

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    private ColorPickerDialog mColorPickerDialog;

    public static DrawingFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
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

        mColorPickerDialog = new ColorPickerDialog(view.getContext());
        mColorPickerDialog.setOnColorClickListener(color ->
                mPresenter.changeBrushColor(getContext(), color)
        );

        /*brashSizeSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                mImageEditorView.setBrushSize(value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });*/

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

    @OnClick(R.id.brushColorButton)
    void onClickBrushColor() {
        mColorPickerDialog.show();
    }

    @Override
    public void setupColorsAdapter(List<EditorColor> colors) {
        ColorAdapter adapter = new ColorAdapter(colors);
        adapter.setOnColorClickListener(editorColor ->
                mPresenter.changeBrushColor(getContext(), editorColor)
        );
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBrushColorChanged(@ColorInt int color) {
        mImageEditorView.setBrushColor(color);
    }
}
