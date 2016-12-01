package net.iquesoft.iquephoto.mvp.presenters.activity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.views.activity.GalleryView;

@InjectViewState
public class GalleryPresenter extends MvpPresenter<GalleryView> {

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
