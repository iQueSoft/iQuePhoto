package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.views.fragment.ToolsView;

@InjectViewState
public class ToolsPresenter extends MvpPresenter<ToolsView> {
    /*private ToolsView mView;
    private EditorView mEditorView;

    private List<Tool> mToolsList = Tool.getToolsList();

    public ToolsPresenter(EditorView editorView) {
        mEditorView = editorView;
    }

    public void init(ToolsView view) {
        mView = view;
        mEditorView.setEditorCommand(NONE);
    }

    public void setupTool(Tool tool) {
        mEditorView.setupToolFragment(tool.getCommand());
    }*/
}
