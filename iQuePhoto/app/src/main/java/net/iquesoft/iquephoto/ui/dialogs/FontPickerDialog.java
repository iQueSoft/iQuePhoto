package net.iquesoft.iquephoto.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.FontsAdapter;
import net.iquesoft.iquephoto.models.Font;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FontPickerDialog extends Dialog {
    @Inject
    List<Font> mFonts;

    @BindView(R.id.fontsRecyclerView)
    RecyclerView fontsList;

    private String mText;
    private Context mContext;

    private Typeface mTypeface;

    private OnFontClickListener mOnFontClickListener;

    public interface OnFontClickListener {
        void onClick(Typeface typeface);
    }

    private boolean mBold;
    private boolean mItalic;

    public FontPickerDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_font_picker);

        ButterKnife.bind(this);

        App.getAppComponent().inject(this);

        initFontsList();
    }

    @OnClick(R.id.applyFontButton)
    void onClickApply() {
        mOnFontClickListener.onClick(mTypeface);
        dismiss();
    }

    @OnClick(R.id.cancelTextStyle)
    void onClickCancel() {
        dismiss();
    }

    private void initFontsList() {
        FontsAdapter fontsAdapter = new FontsAdapter(mFonts);
        fontsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        fontsAdapter.setOnFontClickListener(font ->
                mTypeface = Typeface.createFromAsset(mContext.getAssets(), font.getPath())
        );

        fontsList.setAdapter(fontsAdapter);
    }

    public void setOnFontClickListener(OnFontClickListener onFontClickListener) {
        mOnFontClickListener = onFontClickListener;
    }

    public boolean isItalic() {
        return mItalic;
    }

    public void setItalic(boolean italic) {
        mItalic = italic;
    }

    public boolean isBold() {
        return mBold;
    }

    public void setBold(boolean bold) {
        mBold = bold;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
