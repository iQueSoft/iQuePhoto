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
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.FiltersAdapter;
import net.iquesoft.iquephoto.adapters.OverlaysAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Filter;
import net.iquesoft.iquephoto.model.Overlay;
import net.iquesoft.iquephoto.presenter.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.OverlayFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IFiltersFragmentView;
import net.iquesoft.iquephoto.view.IOverlayFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OverlayFragment extends BaseFragment implements IOverlayFragmentView {

    private boolean mIsHide;
    private boolean mIsSeekBarHide;

    private int intensity;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    private List<Overlay> mOverlayList = Overlay.getOverlaysList();

    private OverlaysAdapter mAdapter;

    private int mCurrentFilter;

    @Inject
    OverlayFragmentPresenterImpl presenter;

    @BindView(R.id.filterSeekBar)
    DiscreteSeekBar seekBar;

    @BindView(R.id.hideFiltersButton)
    ImageView hideFiltersButton;

    @BindView(R.id.overlayRecyclerView)
    RecyclerView recyclerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_overlay, container, false);
        //v.setAlpha(0.8f);

        mUnbinder = ButterKnife.bind(this, v);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        mAdapter = new OverlaysAdapter(mOverlayList);

        mAdapter.setOverlayListener(overlay -> mImageEditorView.setOverlay(getResources().getDrawable(overlay.getImage())));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter);

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                mImageEditorView.setFilterIntensity(value);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    /*@OnClick(R.id.hideFiltersButton)
    public void onClickHideFilters(View view) {
        if (!mIsHide) {
            hideFiltersButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            filtersList.setVisibility(View.GONE);
            mIsHide = true;
        } else {
            hideFiltersButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            filtersList.setVisibility(View.VISIBLE);
            mIsHide = false;
        }
    }*/
}
