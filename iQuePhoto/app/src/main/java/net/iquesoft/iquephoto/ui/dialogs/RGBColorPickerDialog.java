package net.iquesoft.iquephoto.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RGBColorPickerDialog extends Dialog implements DiscreteSeekBar.OnProgressChangeListener {

    private int mColor;

    private int mR;
    private int mG;
    private int mB;

    private String mHexColor;

    private ColorPickerDialog.OnColorClickListener mOnColorClickListener;

    @BindView(R.id.rgbHexTextView)
    TextView rgbHexTextView;

    @BindView(R.id.rgbRValueTextView)
    TextView redValueTextView;

    @BindView(R.id.rgbGValueTextView)
    TextView greenValueTextView;

    @BindView(R.id.rgbBValueTextView)
    TextView blueValueTextView;

    @BindView(R.id.rgbFrameLayout)
    FrameLayout colorView;

    @BindView(R.id.rgbRSeekBar)
    DiscreteSeekBar redSeekBar;

    @BindView(R.id.rgbGSeekBar)
    DiscreteSeekBar greenSeekBar;

    @BindView(R.id.rgbBSeekBar)
    DiscreteSeekBar blueSeekBar;

    public RGBColorPickerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_rgb_color_picker);

        ButterKnife.bind(this);

        mR = redSeekBar.getProgress();
        mG = greenSeekBar.getProgress();
        mB = greenSeekBar.getProgress();

        mColor = Color.rgb(mR, mG, mB);

        mHexColor = String.format("#%06X", (0xFFFFFF & mColor));

        rgbHexTextView.setText(mHexColor);

        redSeekBar.setOnProgressChangeListener(this);
        greenSeekBar.setOnProgressChangeListener(this);
        blueSeekBar.setOnProgressChangeListener(this);
    }

    @OnClick(R.id.rgbApplyButton)
    void onClickApplyButton() {
        mColor = Color.rgb(mR, mG, mB);
        //mOnColorClickListener.onClick(mColor);
        dismiss();
    }

    @OnClick(R.id.rgbCancelButton)
    void onClickCancel() {
        dismiss();
    }

    private void setColor(int red, int green, int blue) {
        mColor = Color.rgb(red, green, blue);

        colorView.setBackgroundColor(mColor);

        mHexColor = String.format("#%06X", (0xFFFFFF & mColor));
        rgbHexTextView.setText(mHexColor);
    }

    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.rgbRSeekBar:
                mR = value;
                redValueTextView.setText(String.valueOf(value));
                setColor(mR, mG, mB);
                break;
            case R.id.rgbGSeekBar:
                mG = value;
                greenValueTextView.setText(String.valueOf(value));
                setColor(mR, mG, mB);
                break;
            case R.id.rgbBSeekBar:
                mB = value;
                blueValueTextView.setText(String.valueOf(value));
                setColor(mR, mG, mB);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

    }

    public void setOnColorClickListener(ColorPickerDialog.OnColorClickListener onColorClickListener) {
        mOnColorClickListener = onColorClickListener;
    }
}
