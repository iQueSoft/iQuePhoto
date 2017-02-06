package net.iquesoft.iquephoto.presentation.presenters.activity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.presentation.views.activity.GalleryView;

@InjectViewState
public class GalleryActivityPresenter extends MvpPresenter<GalleryView> {

    /*public void onBackPressed(RecyclerView recyclerView) {
        Object object = recyclerView.getAdapter();

        if (object != null) {
            String classSimpleName = object.getClass().getSimpleName();
            if (classSimpleName.equalsIgnoreCase(ImageAlbumsAdapter.class.getSimpleName()))
                getViewState().navigateBack();
            else
                getViewState.showFolders();
        } else
            getViewState.navigateBack();
    }*/
}
