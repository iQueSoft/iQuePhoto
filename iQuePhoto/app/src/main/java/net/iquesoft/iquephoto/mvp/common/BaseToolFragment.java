package net.iquesoft.iquephoto.mvp.common;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.Menu;
import android.view.MenuInflater;

import com.arellomobile.mvp.MvpAppCompatFragment;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;

public abstract class BaseToolFragment extends MvpAppCompatFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.removeItem(R.id.action_share);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tool, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}