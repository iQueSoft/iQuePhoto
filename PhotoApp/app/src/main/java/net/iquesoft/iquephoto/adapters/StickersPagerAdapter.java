package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.iquesoft.iquephoto.model.StickersSet;
import net.iquesoft.iquephoto.ui.fragment.ShowStickersFragment;

import java.util.List;

public class StickersPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private List<StickersSet> mStickersSetsList;

    public StickersPagerAdapter(FragmentManager fm, Context context, List<StickersSet> stickersSetList) {
        super(fm);
        mContext = context;
        mStickersSetsList = stickersSetList;
    }

    @Override
    public Fragment getItem(int position) {
        return ShowStickersFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mStickersSetsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


}