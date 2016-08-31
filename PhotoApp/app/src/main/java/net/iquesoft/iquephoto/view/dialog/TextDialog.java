package net.iquesoft.iquephoto.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Button;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.FontsAdapter;
import net.iquesoft.iquephoto.model.Font;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sergey
 */
public class TextDialog extends Dialog {

    @BindView(R.id.fontsList)
    RecyclerView fontsList;

    private boolean bold;
    private boolean italic;

    public TextDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_text);

        ButterKnife.bind(this);

        initFontsList();
    }

    @OnClick(R.id.addTextButton)
    public void onClickAddText() {

    }

    @OnClick(R.id.textCancelButton)
    public void onClickCancel() {
        dismiss();
    }

    public void initFontsList() {
        fontsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fontsList.setAdapter(new FontsAdapter(Font.getFontsList()));
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
