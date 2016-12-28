package net.iquesoft.iquephoto.mvp.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Tool;

import java.util.List;

public interface ToolsView extends MvpView {
    void setupTools(List<Tool> tools);
}
