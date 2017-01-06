package net.iquesoft.iquephoto.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.FramesAdapter;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.NewImageEditorView;
import net.iquesoft.iquephoto.presentation.common.BaseToolFragment;
import net.iquesoft.iquephoto.models.Frame;
import net.iquesoft.iquephoto.presentation.presenters.fragment.FramesPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.FramesView;
import net.iquesoft.iquephoto.util.ActivityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.FRAMES;

public class FramesFragment extends BaseToolFragment implements FramesView {
    @InjectPresenter
    FramesPresenter presenter;

    @BindView(R.id.frameRecyclerView)
    RecyclerView recyclerView;

    private Unbinder mUnbinder;

    private NewImageEditorView mImageEditorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView = (NewImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frames, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityUtil.updateToolbarTitle(R.string.frames, getActivity());
        mImageEditorView.changeTool(FRAMES);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setupAdapter(List<Frame> frames) {
        FramesAdapter adapter = new FramesAdapter(frames);
        adapter.setFramesListener(frame -> presenter.changeOverlay(getContext(), frame));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFrameChanged(Bitmap bitmap) {
        mImageEditorView.setFrame(bitmap);
    }
}
