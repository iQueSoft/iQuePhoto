package net.iquesoft.iquephoto.view.dialog;

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

/**
 * @author Sergey
 */
public class ColorPickerDialog extends Dialog implements DiscreteSeekBar.OnProgressChangeListener {

    private int color;
    private int r, g, b;

    @BindView(R.id.redSeekBarValue)
    TextView redValueTextView;

    @BindView(R.id.greenSeekBarValue)
    TextView greenValueTextView;

    @BindView(R.id.blueSeekBarValue)
    TextView blueValueTextView;

    @BindView(R.id.colorView)
    FrameLayout colorView;

    @BindView(R.id.redSeekBar)
    DiscreteSeekBar redSeekBar;

    @BindView(R.id.greenSeekBar)
    DiscreteSeekBar greenSeekBar;

    @BindView(R.id.blueSeekBar)
    DiscreteSeekBar blueSeekBar;

    public ColorPickerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_color_picker);

        ButterKnife.bind(this);

        r = redSeekBar.getProgress();
        g = greenSeekBar.getProgress();
        b = greenSeekBar.getProgress();

        redSeekBar.setOnProgressChangeListener(this);
        greenSeekBar.setOnProgressChangeListener(this);
        blueSeekBar.setOnProgressChangeListener(this);
    }

    private void setColor(int red, int green, int blue) {
        this.color = Color.rgb(red, green, blue);
        colorView.setBackgroundColor(getColor());
    }

    public int getColor() {
        return color;
    }

    @OnClick(R.id.colorCancelButton)
    public void onClickCancel() {
        dismiss();
    }

    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.redSeekBar:
                r = value;
                redValueTextView.setText(String.valueOf(value));
                setColor(r, g, b);
                break;
            case R.id.greenSeekBar:
                g = value;
                greenValueTextView.setText(String.valueOf(value));
                setColor(r, g, b);
                break;
            case R.id.blueSeekBar:
                b = value;
                blueValueTextView.setText(String.valueOf(value));
                setColor(r, g, b);
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
