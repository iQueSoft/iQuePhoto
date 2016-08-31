package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.PhotoEditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IMainActivityComponent;
import net.iquesoft.iquephoto.presenter.BrightnessFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IBrightnessFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BrightnessFragment extends BaseFragment implements IBrightnessFragmentView {

    private Unbinder unbinder;

    private PhotoEditorView photoEditorView;

    @BindView(R.id.brightnessValueTextView)
    TextView brightnessValueTextView;

    @BindView(R.id.brightnessSeekBar)
    DiscreteSeekBar seekBar;

    @Inject
    BrightnessFragmentPresenterImpl presenter;

    public static BrightnessFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new BrightnessFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brightness, container, false);

        photoEditorView = DataHolder.getInstance().getPhotoEditorView();

        unbinder = ButterKnife.bind(this, v);

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                brightnessValueTextView.setText(String.valueOf(value));
                photoEditorView.doBrightness(value);
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
        unbinder.unbind();
    }
}
