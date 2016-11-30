package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.FramesAdapter;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.mvp.models.Frame;
import net.iquesoft.iquephoto.mvp.presenters.fragment.FramesPresenter;
import net.iquesoft.iquephoto.mvp.views.activity.EditorView;
import net.iquesoft.iquephoto.mvp.views.fragment.FramesView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FramesFragment extends MvpAppCompatFragment implements FramesView {

    private Unbinder mUnbinder;

    private List<Frame> mFrameList = Frame.getFramesList();

    private FramesAdapter mAdapter;

    private ImageEditorView mImageEditorView;

    @Inject
    EditorView editorActivityView;

    @Inject
    FramesPresenter presenter;

    @BindView(R.id.frameRecyclerView)
    RecyclerView recyclerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frames, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mAdapter = new FramesAdapter(mFrameList);
        mAdapter.setFramesListener(frame -> {
            mImageEditorView.setFrame(getResources().getDrawable(frame.getImage()));
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        return view;
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

    @OnClick(R.id.framesBack)
    void onClickBack() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.framesApply)
    void onClickApply() {
        editorActivityView.getImageEditorView().apply(EditorCommand.FRAMES);
        onClickBack();
    }
}
