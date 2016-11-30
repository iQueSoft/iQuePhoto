package net.iquesoft.iquephoto.mvp.presenters.activity;

import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.views.activity.GalleryView;

import javax.inject.Inject;

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
