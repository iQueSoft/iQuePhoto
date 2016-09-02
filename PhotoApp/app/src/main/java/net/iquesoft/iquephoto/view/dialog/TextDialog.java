package net.iquesoft.iquephoto.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.FontsAdapter;
import net.iquesoft.iquephoto.model.Font;
import net.iquesoft.iquephoto.view.fragment.TextFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sergey
 */
public class TextDialog extends Dialog {

    //private static final String TAG = TextDialog.class.getSimpleName();
    private Context context;
    private TextFragment textFragment;

    private int color;
    private String text;
    private Typeface typeface;

    @BindView(R.id.textPreview)
    TextView textPreview;

    @BindView(R.id.fontsList)
    RecyclerView fontsList;

    private boolean bold;
    private boolean italic;

    public TextDialog(Context context, TextFragment textFragment) {
        super(context);
        this.context = context;
        this.textFragment = textFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_text);

        ButterKnife.bind(this);

        if (text.length() != 0)
            textPreview.setText(text);

        textPreview.setTextColor(color);

        initFontsList();
    }

    @OnClick(R.id.applyTextStyle)
    public void onClickApplyTextStyle() {
        textFragment.setTypeface(typeface);
        dismiss();
    }

    @OnClick(R.id.cancelTextStyle)
    public void onClickCancel() {
        dismiss();
    }

    public void initFontsList() {
        FontsAdapter fontsAdapter = new FontsAdapter(Font.getFontsList());
        fontsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fontsAdapter.setFontsListener(font -> {
            typeface = Typeface.createFromAsset(getContext().getAssets(), font.getTypeface());
            textPreview.setTypeface(typeface);
        });
        fontsList.setAdapter(fontsAdapter);

    }


    /**
     * Show this dialog.
     *
     * @param text  need for text preview;
     * @param color is text preview text color.
     */
    public void showDialog(@Nullable String text, @Nullable int color) {
        this.text = text;
        this.color = color;
        show();
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public int getColor() {
        return 0;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }
}
