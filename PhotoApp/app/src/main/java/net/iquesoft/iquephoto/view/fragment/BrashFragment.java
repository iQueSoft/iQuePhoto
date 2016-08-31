package net.iquesoft.iquephoto.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.dialogs.ColorPickerDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sergey
 */
public class BrashFragment extends Fragment {

    private ColorPickerDialog colorPickerDialog;

    @BindView(R.id.brushColorButton)
    ImageView brushColorButton;

    public static BrashFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new BrashFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.brash_frag, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.brushColorButton)
    public void onClickBrushColor(View view) {
        colorPickerDialog = new ColorPickerDialog(getContext());
        colorPickerDialog.show();
    }

}
