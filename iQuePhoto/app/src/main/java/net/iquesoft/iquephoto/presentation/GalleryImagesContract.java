package net.iquesoft.iquephoto.presentation;

import net.iquesoft.iquephoto.common.BasePresenter;
import net.iquesoft.iquephoto.common.BaseView;

public interface GalleryImagesContract {

    interface View extends BaseView<Presenter> {
        boolean isActive();
    }

    interface Presenter extends BasePresenter {

    }
}
