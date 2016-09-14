package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IMainActivityComponent;
import net.iquesoft.iquephoto.presenter.DrawFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IDrawFragmentView;
import net.iquesoft.iquephoto.view.dialog.ColorPickerDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sergey
 */
public class DrawFragment extends BaseFragment implements IDrawFragmentView {

    @Inject
    DrawFragmentPresenterImpl presenter;

    private Unbinder unbinder;
    private ColorPickerDialog colorPickerDialog;

    public static DrawFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new DrawFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brush, container, false);
        v.setAlpha(0.8f);

        unbinder = ButterKnife.bind(this, v);

        colorPickerDialog = new ColorPickerDialog(v.getContext());

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.brushColorButton)
    public void onClickBrushColor() {
        colorPickerDialog.show();
    }

}
