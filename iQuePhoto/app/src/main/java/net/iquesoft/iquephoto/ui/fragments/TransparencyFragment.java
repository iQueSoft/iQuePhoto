package net.iquesoft.iquephoto.ui.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.models.ParcelablePaint;
import net.iquesoft.iquephoto.presentation.presenters.fragment.TransparencyPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.IntensityView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * This Fragment uses for change Stickers or Texts transparency (from = 20%, to = 100%)
 * from ImageEditorView.
 */
public class TransparencyFragment extends MvpAppCompatFragment implements IntensityView {
    public static final String ARG_PARAM = "paint";

    @InjectPresenter
    TransparencyPresenter mPresenter;

    @ProvidePresenter
    TransparencyPresenter provideTransparencyPresenter() {
        return new TransparencyPresenter(getArguments());
    }
    
    @BindView(R.id.transparencySeekBar)
    DiscreteSeekBar mSeekBar;

    @BindView(R.id.transparencyCurrentValueTextView)
    TextView mCurrentValueTextView;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    /**
     * @param paint is EditorSticker or EditorText Paint.
     */
    public static TransparencyFragment newInstance(Paint paint) {
        TransparencyFragment fragment = new TransparencyFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM, new ParcelablePaint(paint));

        fragment.setArguments(args);

        return fragment;
    }

    public TransparencyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mImageEditorView =
                (ImageEditorView) getActivity().findViewById(R.id.image_editor_view);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.removeItem(R.id.action_share);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tool, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transparency, container, false);

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

    @Override
    public void onTransparencyChanged(String value) {
        mCurrentValueTextView.setText(value);
        mImageEditorView.invalidate();
    }

    @OnClick(R.id.transparencyApplyButton)
    void onClickApply() {
        mPresenter.applyChanges();
    }

    @OnClick(R.id.transparencyCancelButton)
    void onClickCancel() {
        mPresenter.cancelChanges();
    }
}