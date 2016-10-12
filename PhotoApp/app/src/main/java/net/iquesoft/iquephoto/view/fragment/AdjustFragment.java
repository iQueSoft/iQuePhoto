package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.adapters.AdjustAdapter;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Adjust;
import net.iquesoft.iquephoto.presenter.AdjustFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IBrightnessFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AdjustFragment extends BaseFragment implements IBrightnessFragmentView {
    private int mAdjustPosition;

    private int mBrightnessValue;
    private int mContrastValue;
    private int mSaturationValue;
    private int mWarmthValue;

    private int mCurrentAdjust;

    private boolean mIsHide;
    private boolean mSeekBarIsHide = true;

    private Unbinder mUnbinder;

    private AdjustAdapter mAdapter;

    @BindView(R.id.hideAdjust)
    ImageView hideAdjust;

    @BindView(R.id.adjustRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.adjustSeekBar)
    DiscreteSeekBar seekBar;

    private ImageEditorView mImageEditorView;

    @Inject
    AdjustFragmentPresenterImpl presenter;

    public static AdjustFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new AdjustFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_adjust, container, false);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        mUnbinder = ButterKnife.bind(this, v);

        mAdapter = new AdjustAdapter(Adjust.getAdjustList());

        mAdapter.setAdjustListener((adjust, position) -> {
            mAdjustPosition = position;

            if (!mSeekBarIsHide) {
                seekBar.setVisibility(View.GONE);
                mSeekBarIsHide = true;
            }

            switch (adjust.getTitle()) {
                case R.string.contrast:
                    mCurrentAdjust = adjust.getTitle();
                    mContrastValue = adjust.getValue();

                    if (mSeekBarIsHide) {
                        seekBar.setProgress(mContrastValue);
                        seekBar.setVisibility(View.VISIBLE);
                        mSeekBarIsHide = false;
                    }
                    break;
                case R.string.brightness:
                    mCurrentAdjust = adjust.getTitle();
                    mBrightnessValue = adjust.getValue();

                    if (mSeekBarIsHide) {
                        seekBar.setProgress(mBrightnessValue);
                        seekBar.setVisibility(View.VISIBLE);
                        mSeekBarIsHide = false;
                    }
                    break;
                case R.string.saturation:
                    mCurrentAdjust = adjust.getTitle();
                    mSaturationValue = adjust.getValue();

                    if (mSeekBarIsHide) {
                        seekBar.setProgress(mSaturationValue);
                        seekBar.setVisibility(View.VISIBLE);
                        mSeekBarIsHide = false;
                    }
                    break;
                case R.string.warmth:
                    mCurrentAdjust = adjust.getTitle();
                    mWarmthValue = adjust.getValue();

                    if (mSeekBarIsHide) {
                        seekBar.setProgress(mWarmthValue);
                        seekBar.setVisibility(View.VISIBLE);
                        mSeekBarIsHide = false;
                    }
                    break;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));

        recyclerView.setAdapter(mAdapter);

        //v.setAlpha(0.8f);

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                switch (mCurrentAdjust) {
                    case R.string.contrast:
                        mContrastValue = value;
                        break;
                    case R.string.brightness:
                        mBrightnessValue = value;
                        mImageEditorView.setBrightnessValue(mBrightnessValue);
                        break;
                    case R.string.saturation:
                        mSaturationValue = value;
                        break;
                    case R.string.warmth:
                        mWarmthValue = value;
                        break;
                }
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
        mUnbinder.unbind();
    }

    @OnClick(R.id.hideAdjust)
    void onClickHide(View view) {
        if (!mIsHide) {
            mIsHide = true;
            hideAdjust.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            recyclerView.setVisibility(View.GONE);
        } else {
            mIsHide = false;
            hideAdjust.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
