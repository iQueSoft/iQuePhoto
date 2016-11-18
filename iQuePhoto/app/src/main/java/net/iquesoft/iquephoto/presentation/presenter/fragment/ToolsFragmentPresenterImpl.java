package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.presentation.view.fragment.ToolsView;

import java.util.List;

import javax.inject.Inject;

public class ToolsFragmentPresenterImpl implements ToolsPresenter {

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
