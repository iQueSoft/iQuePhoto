package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.presentation.view.editor.tools.ToolsView;

import java.util.List;

import javax.inject.Inject;

public class ToolsFragmentPresenterImpl implements IToolsFragmentPresenter {

    private ToolsView mView;

    private List<Tool> mToolsList = Tool.getToolsList();

    @Inject
    public ToolsFragmentPresenterImpl() {

    }

    @Override
    public void init(ToolsView view) {
        mView = view;
    }
}
