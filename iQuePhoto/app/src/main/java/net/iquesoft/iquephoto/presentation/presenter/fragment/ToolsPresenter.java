package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.presentation.view.fragment.ToolsView;

public interface ToolsPresenter extends BaseFragmentPresenter<ToolsView> {
    void setupTool(Tool tool);
}
