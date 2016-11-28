package net.iquesoft.iquephoto.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.di.components.EditorComponent;
import net.iquesoft.iquephoto.presentation.presenter.fragment.DrawingPresenterImpl;
import net.iquesoft.iquephoto.ui.dialog.ColorPickerDialog;
import net.iquesoft.iquephoto.ui.dialog.RGBColorPickerDialog;
import net.iquesoft.iquephoto.presentation.view.fragment.DrawingView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DrawingFragment extends BaseFragment implements DrawingView {

    @Inject
    DrawingPresenterImpl presenter;

    @BindView(R.id.drawingSettings)
    LinearLayout drawingSettings;

    /*@BindView(R.id.brashSizeSeekBar)
    DiscreteSeekBar brashSizeSeekBar;*/

    private ColorPickerDialog mColorPickerDialog;

    private ImageEditorView mImageEditorView;

    private Unbinder mUnbinder;

    private RGBColorPickerDialog RGBColorPickerDialog;

    public static DrawingFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new DrawingFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(EditorComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawing, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        mColorPickerDialog = new ColorPickerDialog(view.getContext());

        mColorPickerDialog.setOnColorClickListener(color -> {
            mImageEditorView.setBrushColor(color);
        });

        RGBColorPickerDialog = new RGBColorPickerDialog(view.getContext());

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
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.brushColorButton)
    public void onClickBrushColor() {
        mColorPickerDialog.show();
    }
}
