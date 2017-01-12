package net.iquesoft.iquephoto.presentation.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.models.EditorColor;
import net.iquesoft.iquephoto.presentation.views.fragment.ColorsView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class ColorsPresenter extends MvpPresenter<ColorsView> {
    @Inject
    List<EditorColor> mColors;

    public ColorsPresenter() {
        App.getAppComponent().inject(this);

        getViewState().setupAdapter(mColors);
    }

}
