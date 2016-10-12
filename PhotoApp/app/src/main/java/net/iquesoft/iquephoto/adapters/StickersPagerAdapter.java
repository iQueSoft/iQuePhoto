package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.iquesoft.iquephoto.model.StickersSet;
import net.iquesoft.iquephoto.view.fragment.ShowStickersFragment;

import java.util.List;

public class StickersPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private List<StickersSet> mStickersSets = StickersSet.getStickersSetsList();

    public StickersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ShowStickersFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mStickersSets.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(mStickersSets.get(position).getTitle());
    }
}