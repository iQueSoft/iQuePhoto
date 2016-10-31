package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.view.IToolsFragmentView;

import java.util.List;

import javax.inject.Inject;

public class ToolsFragmentPresenterImpl implements IToolsFragmentPresenter {

    private IToolsFragmentView mView;

    private List<Tool> mToolsList = Tool.getToolsList();

    @Inject
    public ToolsFragmentPresenterImpl() {

    }

    @Override
    public void init(IToolsFragmentView view) {
        mView = view;
    }
}
