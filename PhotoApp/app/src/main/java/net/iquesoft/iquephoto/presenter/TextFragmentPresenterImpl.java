package net.iquesoft.iquephoto.presenter;

import net.iquesoft.iquephoto.view.ITextFragmentView;

import javax.inject.Inject;

public class TextFragmentPresenterImpl implements ITextFragmentPresenter {

    private ITextFragmentView view;

    @Inject
    public TextFragmentPresenterImpl() {

    }

    @Override
    public void init(ITextFragmentView view) {
        this.view = view;
    }
}
