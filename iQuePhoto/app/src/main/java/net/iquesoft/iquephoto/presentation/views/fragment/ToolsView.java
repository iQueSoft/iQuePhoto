package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Tool;

import java.util.List;

public interface ToolsView extends MvpView {
    void setupTools(List<Tool> tools);
}
