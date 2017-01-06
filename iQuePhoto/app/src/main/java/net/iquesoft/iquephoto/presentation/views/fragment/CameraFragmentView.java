package net.iquesoft.iquephoto.presentation.views.fragment;

import android.graphics.Paint;

import com.arellomobile.mvp.MvpView;

public interface CameraFragmentView extends MvpView {
    void changeFilter(Paint paint);

}
