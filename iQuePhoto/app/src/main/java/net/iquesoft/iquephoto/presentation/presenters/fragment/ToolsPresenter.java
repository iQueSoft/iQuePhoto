package net.iquesoft.iquephoto.presentation.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.models.Tool;
import net.iquesoft.iquephoto.presentation.views.fragment.ToolsView;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

@InjectViewState
public class ToolsPresenter extends MvpPresenter<ToolsView> {
    @Inject
    Lazy<List<Tool>> mTools;

    public ToolsPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupTools(mTools.get());
    }
}