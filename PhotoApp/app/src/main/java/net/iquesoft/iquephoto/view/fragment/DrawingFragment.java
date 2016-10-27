package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.presenter.DrawingFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IDrawingFragmentView;
import net.iquesoft.iquephoto.view.dialog.ColorPickerDialog;
import net.iquesoft.iquephoto.view.dialog.RGBColorPickerDialog;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DrawingFragment extends BaseFragment implements IDrawingFragmentView {

    @Inject
    DrawingFragmentPresenterImpl presenter;

    @BindView(R.id.drawingSettings)
    LinearLayout drawingSettings;

    @BindView(R.id.hideDrawingButton)
    ImageView hideDrawingButton;

    @BindView(R.id.brashSizeSeekBar)
    DiscreteSeekBar brashSizeSeekBar;

    private ColorPickerDialog mColorPickerDialog;

    private ImageEditorView mImageEditorView;

    private boolean mIsHide;

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
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawing, container, false);

        mUnbinder = ButterKnife.bind(this, v);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        mColorPickerDialog = new ColorPickerDialog(v.getContext());

        mColorPickerDialog.setListener(color -> {
            mImageEditorView.setBrushColor(color);
        });

        RGBColorPickerDialog = new RGBColorPickerDialog(v.getContext());

        brashSizeSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
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
        });

        return v;
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

    @OnClick(R.id.hideDrawingButton)
    public void onClickHide() {
        if (!mIsHide) {
            mIsHide = true;
            hideDrawingButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            drawingSettings.setVisibility(View.GONE);
        } else {
            mIsHide = false;
            hideDrawingButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            drawingSettings.setVisibility(View.VISIBLE);
        }
    }

}
