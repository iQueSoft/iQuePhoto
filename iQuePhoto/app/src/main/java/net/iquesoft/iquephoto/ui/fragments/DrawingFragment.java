package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.NewImageEditorView;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.presentation.presenters.fragment.DrawingPresenter;
import net.iquesoft.iquephoto.ui.dialogs.ColorPickerDialog;
import net.iquesoft.iquephoto.presentation.views.fragment.DrawingView;
import net.iquesoft.iquephoto.util.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.DRAWING;

public class DrawingFragment extends ToolFragment implements DrawingView {
    @InjectPresenter
    DrawingPresenter presenter;

    @BindView(R.id.drawingSettings)
    LinearLayout drawingSettings;

    /*@BindView(R.id.brashSizeSeekBar)
    DiscreteSeekBar brashSizeSeekBar;*/

    private Unbinder mUnbinder;

    private NewImageEditorView mImageEditorView;

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
        mImageEditorView = (NewImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawing, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mColorPickerDialog = new ColorPickerDialog(view.getContext());
        mColorPickerDialog.setOnColorClickListener(color ->
                presenter.changeBrushColor(getContext(), color)
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
        ActivityUtil.updateToolbarTitle(R.string.drawing, getActivity());
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
    public void onBrushColorChanged(@ColorInt int color) {
        mImageEditorView.setBrushColor(color);
    }
}
