package net.iquesoft.iquephoto.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ColorAdapter;
import net.iquesoft.iquephoto.models.EditorColor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorPickerDialog extends Dialog {
    private EditorColor mColor;
    private Context mContext;

    private RGBColorPickerDialog mRGBColorPickerDialog;
    private ARGBColorPickerDialog mARGBColorPickerDialog;

    private OnColorClickListener mOnColorClickListener;

    public interface OnColorClickListener {
        void onClick(EditorColor editorColor);
    }

    private ColorAdapter mAdapter;

    @BindView(R.id.colorRecyclerView)
    RecyclerView recyclerView;

    public ColorPickerDialog(Context context) {
        super(context);
        mContext = context;

        mRGBColorPickerDialog = new RGBColorPickerDialog(context);
        mARGBColorPickerDialog = new ARGBColorPickerDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_color_picker);

        ButterKnife.bind(this);

        mAdapter = new ColorAdapter(EditorColor.getColorsList());
        mAdapter.setOnColorClickListener(editorColor ->
                mColor = editorColor
        );

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.applyColor)
    void onClickApply() {
        mOnColorClickListener.onClick(mColor);
        dismiss();
    }

    @OnClick(R.id.cancelColor)
    void onClickCancel() {
        dismiss();
    }

    public void setOnColorClickListener(OnColorClickListener onColorClickListener) {
        mOnColorClickListener = onColorClickListener;

        mRGBColorPickerDialog.setOnColorClickListener(onColorClickListener);
        mARGBColorPickerDialog.setOnColorClickListener(onColorClickListener);
    }

    @OnClick(R.id.rgbButton)
    void onClickRGB() {
        dismiss();
        mRGBColorPickerDialog.show();
    }

    @OnClick(R.id.argbButton)
    void onClikcARGB() {
        dismiss();
        mARGBColorPickerDialog.show();
    }

}
