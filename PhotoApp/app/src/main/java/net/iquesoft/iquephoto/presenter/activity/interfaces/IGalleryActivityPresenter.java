package net.iquesoft.iquephoto.presenter.activity.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import net.iquesoft.iquephoto.model.ImageFolder;

public interface IGalleryActivityPresenter {
    void fetchImages(Context context);

    void folderClicked(ImageFolder imageFolder);

    void onBackPressed(RecyclerView recyclerView);
}
