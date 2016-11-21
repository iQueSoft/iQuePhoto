package net.iquesoft.iquephoto.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.OverlaysAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Overlay;
import net.iquesoft.iquephoto.presentation.presenter.fragment.OverlaysPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.OverlayView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OverlayFragment extends BaseFragment implements OverlayView {

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    private OverlaysAdapter mAdapter;
    private List<Overlay> mOverlayList = Overlay.getOverlaysList();

    @Inject
    OverlaysPresenterImpl presenter;

    @BindView(R.id.overlaySeekBar)
    DiscreteSeekBar seekBar;

    @BindView(R.id.overlayRecyclerView)
    RecyclerView recyclerView;

    @Inject
    EditorView editorActivityView;

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

        mUnbinder = ButterKnife.bind(this, v);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        mAdapter = new OverlaysAdapter(mOverlayList);

        mAdapter.setOnOverlayClickListener(overlay -> {
            mImageEditorView.setOverlay(overlay.getImage());
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter);

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                mImageEditorView.setOverlayOpacity(value);
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

    @OnClick(R.id.overlayCancel)
    void onClickBack() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.overlayApply)
    void onClickApply() {
        editorActivityView.getImageEditorView().apply(EditorCommand.OVERLAY);
        editorActivityView.navigateBack(true);
    }
}
