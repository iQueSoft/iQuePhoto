package net.iquesoft.iquephoto.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.iquesoft.iquephoto.model.StickersSet;
import net.iquesoft.iquephoto.view.fragment.ShowStickersFragment;

/**
 * Adapter for ViewPager in {@link net.iquesoft.iquephoto.view.fragment.StickersFragment} that returns a fragment corresponding to
 * one of the pages with stickers sets {@link StickersSet}.
 */
public class StickersPagerAdapter extends FragmentPagerAdapter {

    public StickersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ShowStickersFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return StickersSet.getStickersSetsList().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}