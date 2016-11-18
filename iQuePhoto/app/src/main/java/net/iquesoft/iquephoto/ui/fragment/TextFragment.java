package net.iquesoft.iquephoto.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.ui.dialog.ColorPickerDialog;
import net.iquesoft.iquephoto.ui.dialog.FontPickerDialog;
import net.iquesoft.iquephoto.ui.dialog.RGBColorPickerDialog;
import net.iquesoft.iquephoto.presentation.presenter.fragment.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.fragment.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.EditorCommand.TEXT;

public class TextFragment extends BaseFragment implements TextView {

    private Context mContext;

    private int mColor = Color.BLACK;

    private Typeface mTypeface = Typeface.DEFAULT;

    private Unbinder mUnbinder;

    private FontPickerDialog mFontPickerDialog;
    private ColorPickerDialog mColorPickerDialog;
    private RGBColorPickerDialog RGBColorPickerDialog;

    @Inject
    EditorView editorActivityView;

    @Inject
    TextFragmentPresenterImpl presenter;

    @BindView(R.id.textOpacityValue)
    android.widget.TextView opacityValueTextView;

    @BindView(R.id.textOpacitySeekBar)
    DiscreteSeekBar seekBar;

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

        mFontPickerDialog = new FontPickerDialog(v.getContext());
        mFontPickerDialog.setOnFontClickListener(typeface -> {
            mTypeface = typeface;
        });

        mColorPickerDialog = new ColorPickerDialog(mContext);
        mColorPickerDialog.setOnColorClickListener(color -> {
            mColor = color;
        });

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
        presenter.addText(editText.getText().toString(), mTypeface, mColor, seekBar.getProgress());
    }

    @OnClick(R.id.selectTextColorButton)
    void onClickTextColorButton() {
        mColorPickerDialog.show();
    }

    @OnClick(R.id.selectFontButton)
    void onClickTextButton() {
        mFontPickerDialog.show();
    }

    @OnClick(R.id.textBackButton)
    void onClickBack() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.textApplyButton)
    void onClickApply() {
        editorActivityView.getImageEditorView().apply(TEXT);
    }

    @Override
    public void showToastMessage(@StringRes int messageText) {
        Toast.makeText(mContext, getResources().getString(messageText), Toast.LENGTH_SHORT).show();
    }
}