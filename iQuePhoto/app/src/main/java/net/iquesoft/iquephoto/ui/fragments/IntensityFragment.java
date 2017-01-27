package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.models.ParcelablePaint;
import net.iquesoft.iquephoto.presentation.presenters.fragment.IntensityPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.IntensityView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IntensityFragment extends MvpAppCompatFragment implements IntensityView {
    public static final String ARG_PARAM = "paint";

    @InjectPresenter
    IntensityPresenter mPresenter;

    @ProvidePresenter
    IntensityPresenter provideIntensityPresenter() {
        return new IntensityPresenter(getArguments());
    }

    @BindView(R.id.intensitySeekBar)
    DiscreteSeekBar mSeekBar;

    @BindView(R.id.intensityCurrentValueTextView)
    TextView currentValueTextView;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    public static IntensityFragment newInstance(ParcelablePaint parcelablePaint) {
        IntensityFragment fragment = new IntensityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM, parcelablePaint);

        fragment.setArguments(args);

        return fragment;
    }

    public IntensityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView =
                (ImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intensity, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                mPresenter.progressChanged(value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}