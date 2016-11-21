package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.ToolsPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.ToolsView;

import java.util.List;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.NONE;

public class ToolsFragmentPresenterImpl implements ToolsPresenter {
    private ToolsView mView;
    private EditorView mEditorView;

    private List<Tool> mToolsList = Tool.getToolsList();

    @Inject
    public ToolsFragmentPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(ToolsView view) {
        mView = view;
        mEditorView.setEditorCommand(NONE);
    }

    @Override
    public void setupTool(Tool tool) {
        mEditorView.setupToolFragment(tool.getCommand());
    }
}
