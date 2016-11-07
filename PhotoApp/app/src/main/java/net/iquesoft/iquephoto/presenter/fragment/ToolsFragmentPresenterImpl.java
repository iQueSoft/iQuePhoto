package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.presenter.fragment.interfaces.IToolsFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.IToolsFragmentView;

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
