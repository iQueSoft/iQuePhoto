package net.iquesoft.iquephoto.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.model.Text;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.view.dialog.FontPickerDialog;
import net.iquesoft.iquephoto.view.dialog.RGBColorPickerDialog;
import net.iquesoft.iquephoto.presenter.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.ITextFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TextFragment extends BaseFragment implements ITextFragmentView {

    private Context mContext;

    private int color = 0x7f0b004f; // Default color white

    private String mText;
    private Typeface mTypeface;

    private boolean isHide = false;
    private boolean isDeleteActive = false;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    private FontPickerDialog fontPickerDialog;
    private RGBColorPickerDialog RGBColorPickerDialog;

    @Inject
    TextFragmentPresenterImpl presenter;

    @BindView(R.id.textOpacityValue)
    TextView opacityValueTextView;

    @BindView(R.id.textOpacitySeekBar)
    DiscreteSeekBar seekBar;

    @BindView(R.id.hideTextSettingsButton)
    ImageView hideTextSettings;

    @BindView(R.id.textSettingsLayout)
    LinearLayout textSettingsLayout;

    @BindView(R.id.textField)
    EditText editText;

    public static TextFragment newInstance() {
        return new TextFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_text, container, false);

        mUnbinder = ButterKnife.bind(this, v);

        mContext = v.getContext();

        mImageEditorView = DataHolder.getInstance().getEditorView();

        fontPickerDialog = new FontPickerDialog(v.getContext());
        RGBColorPickerDialog = new RGBColorPickerDialog(v.getContext());

        opacityValueTextView.setText(String.valueOf(seekBar.getProgress()));

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                opacityValueTextView.setText(String.valueOf(value));
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
    public void onResume() {
        super.onResume();
        presenter.init(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.addTextButton)
    void onClickAddText() {
        mText = editText.getText().toString();
        if (!mText.isEmpty()) {
            color = RGBColorPickerDialog.getColor();
            mTypeface = fontPickerDialog.getTypeface();

            presenter.addText(mText, color, mTypeface, seekBar.getProgress());
        } else {
            Toast.makeText(mContext, getResources().getString(R.string.text_is_empty), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.hideTextSettingsButton)
    void onClickHide() {
        if (!isHide) {
            hideTextSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            textSettingsLayout.setVisibility(View.GONE);
            isHide = true;
        } else {
            hideTextSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            textSettingsLayout.setVisibility(View.VISIBLE);
            isHide = false;
        }
    }

    @OnClick(R.id.textColorButton)
    void onClickTextColorButton() {
        RGBColorPickerDialog.show();
    }

    @OnClick(R.id.textButton)
    void onClickTextButton() {
        mText = editText.getText().toString();
        color = RGBColorPickerDialog.getColor();
        if (mText.length() > 0) {
            fontPickerDialog.showDialog(mText, color);
        }

    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
    }

    @Override
    public void onAddTextComplete(Text text) {
        mImageEditorView.addText(text);
        Toast.makeText(mContext, getResources().getString(R.string.text_added), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteTextComplete(Text text) {
        if (isDeleteActive) {
            //editorView.deleteText(text);
        }
    }
}