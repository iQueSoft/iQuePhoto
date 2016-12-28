package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.mvp.models.Tool;
import net.iquesoft.iquephoto.mvp.views.fragment.ToolsView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class ToolsPresenter extends MvpPresenter<ToolsView> {
    @Inject
    List<Tool> mTools;

    public ToolsPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupTools(mTools);
    }
}
