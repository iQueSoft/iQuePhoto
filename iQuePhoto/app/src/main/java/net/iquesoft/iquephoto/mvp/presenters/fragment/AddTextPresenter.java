package net.iquesoft.iquephoto.mvp.presenters.fragment;

import android.graphics.Typeface;

import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.mvp.models.Text;
import net.iquesoft.iquephoto.mvp.views.fragment.AddTextView;

public class AddTextPresenter extends MvpPresenter<AddTextView> {

    public void addText(String text, Typeface typeface, int color, int opacity) {
        if (!text.isEmpty()) {
            int opacityValue = opacity * (int) 2.5f;
            Text texts = new Text(text, typeface, color, opacityValue);
            getViewState().addText(texts);
        } else
            getViewState().showToastMessage(R.string.text_is_empty);
    }
}
