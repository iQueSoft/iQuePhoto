package net.iquesoft.iquephoto.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.NewImageEditorView;
import net.iquesoft.iquephoto.presentation.common.ToolFragment;
import net.iquesoft.iquephoto.models.Text;
import net.iquesoft.iquephoto.ui.dialogs.ColorPickerDialog;
import net.iquesoft.iquephoto.ui.dialogs.FontPickerDialog;
import net.iquesoft.iquephoto.presentation.presenters.fragment.AddTextPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.AddTextView;
import net.iquesoft.iquephoto.util.ActivityUtil;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.TEXT;

public class TextFragment extends ToolFragment implements AddTextView {
    @InjectPresenter
    AddTextPresenter presenter;

    @BindView(R.id.textSettingsLayout)
    LinearLayout textSettingsLayout;

    @BindView(R.id.textField)
    EditText editText;

    private Context mContext;

    private int mColor = Color.BLACK;

    private Typeface mTypeface = Typeface.DEFAULT;

    private Unbinder mUnbinder;

    private FontPickerDialog mFontPickerDialog;
    private ColorPickerDialog mColorPickerDialog;

    private NewImageEditorView mImageEditorView;

    public static TextFragment newInstance() {
        return new TextFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView = (NewImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mContext = view.getContext();

        mFontPickerDialog = new FontPickerDialog(view.getContext());
        mFontPickerDialog.setOnFontClickListener(typeface -> mTypeface = typeface);

        mColorPickerDialog = new ColorPickerDialog(mContext);
        mColorPickerDialog.setOnColorClickListener(color ->
                presenter.changeTextColor(getContext(), color)
        );


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImageEditorView.changeTool(TEXT);
        ActivityUtil.updateToolbarTitle(R.string.text, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void addText(Text text) {
        mImageEditorView.addText(text);
    }

    @Override
    public void onTextColorChanged(@ColorInt int color) {

    }

    @Override
    public void showToastMessage(@StringRes int messageText) {
        Toast.makeText(mContext, getResources().getString(messageText), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.addTextButton)
    void onClickAddText() {
        presenter.addText(editText.getText().toString(), mTypeface, mColor, 255);
    }

    @OnClick(R.id.selectTextColorButton)
    void onClickTextColorButton() {
        mColorPickerDialog.show();
    }

    @OnClick(R.id.selectFontButton)
    void onClickTextButton() {
        mFontPickerDialog.show();
    }
}