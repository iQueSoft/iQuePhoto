package net.iquesoft.iquephoto.presenter.fragment;

import net.iquesoft.iquephoto.presenter.fragment.interfaces.ITextFragmentPresenter;
import net.iquesoft.iquephoto.view.fragment.interfaces.ITextFragmentView;

import javax.inject.Inject;

public class TextFragmentPresenterImpl implements ITextFragmentPresenter {

    private ITextFragmentView mView;

    @Inject
    public TextFragmentPresenterImpl() {

    }

    @Override
    public void init(ITextFragmentView view) {
        mView = view;
    }


}
