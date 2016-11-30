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

public class ARGBColorPickerDialog extends Dialog implements DiscreteSeekBar.OnProgressChangeListener {

    private int mColor;

    private int mR;
    private int mG;
    private int mB;
    private int mA;

    private ColorPickerDialog.OnColorClickListener mOnColorClickListener;

    @BindView(R.id.argbAValueTextView)
    TextView argbAValueTextView;

    @BindView(R.id.argbRValueTextView)
    TextView argbRValueTextView;

    @BindView(R.id.argbGValueTextView)
    TextView argbGValueTextView;

    @BindView(R.id.argbBValueTextView)
    TextView argbBValueTextView;

    @BindView(R.id.argbColorFrameLayout)
    FrameLayout argbColorFrameLayout;

    @BindView(R.id.argbASeekBar)
    DiscreteSeekBar rgbaASeekBar;

    @BindView(R.id.argbRSeekBar)
    DiscreteSeekBar argbRSeekBar;

    @BindView(R.id.argbGSeekBar)
    DiscreteSeekBar argbGSeekBar;

    @BindView(R.id.argbBSeekBar)
    DiscreteSeekBar argbBSeekBar;

    public void setOnColorClickListener(ColorPickerDialog.OnColorClickListener onColorClickListener) {
        mOnColorClickListener = onColorClickListener;
    }

    public ARGBColorPickerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_argb_color_picker);

        ButterKnife.bind(this);

        mA = rgbaASeekBar.getProgress();
        mR = argbRSeekBar.getProgress();
        mG = argbGSeekBar.getProgress();
        mB = argbGSeekBar.getProgress();

        mColor = Color.argb(mA, mR, mG, mB);

        rgbaASeekBar.setOnProgressChangeListener(this);
        argbRSeekBar.setOnProgressChangeListener(this);
        argbGSeekBar.setOnProgressChangeListener(this);
        argbBSeekBar.setOnProgressChangeListener(this);

    }

    @OnClick(R.id.argbApplyButton)
    public void onClickApplyButton() {
        mColor = Color.argb(mA, mR, mG, mB);
        mOnColorClickListener.onClick(mColor);
        dismiss();
    }

    @OnClick(R.id.argbCancelButton)
    public void onClickCancel() {
        dismiss();
    }

    private void setColor(int alpha, int red, int green, int blue) {
        argbColorFrameLayout.setBackgroundColor(Color.argb(alpha, red, green, blue));
    }

    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.argbASeekBar:
                mA = value;
                argbAValueTextView.setText(String.valueOf(value));
                setColor(mA, mR, mG, mB);
                break;
            case R.id.argbRSeekBar:
                mR = value;
                argbRValueTextView.setText(String.valueOf(value));
                setColor(mA, mR, mG, mB);
                break;
            case R.id.argbGSeekBar:
                mG = value;
                argbGValueTextView.setText(String.valueOf(value));
                setColor(mA, mR, mG, mB);
                break;
            case R.id.argbBSeekBar:
                mB = value;
                argbBValueTextView.setText(String.valueOf(value));
                setColor(mA, mR, mG, mB);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

    }


}
