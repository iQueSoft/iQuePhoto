package net.iquesoft.iquephoto.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.StickersPagerAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.StickersSet;
import net.iquesoft.iquephoto.presentation.presenter.fragment.StickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.fragment.StickersView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StickersToolFragment extends BaseFragment implements StickersView {

    private List<StickersSet> mStickersSetsList = StickersSet.getStickersSetsList();

    private Unbinder mUnbinder;

    private StickersPagerAdapter mPagerAdapter;

    @Inject
    StickersFragmentPresenterImpl presenter;

    @BindView(R.id.stickerTabLayout)
    TabLayout tabLayout;

    @BindView(R.id.stickersViewPager)
    ViewPager viewPager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stickers, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mPagerAdapter = new StickersPagerAdapter(getChildFragmentManager(), getContext(), mStickersSetsList);
        viewPager.setAdapter(mPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            StickersSet stickersSet = mStickersSetsList.get(i);
            tab.setIcon(stickersSet.getIcon());
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private class StickersPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        private List<StickersSet> mStickersSetsList;

        private StickersPagerAdapter(FragmentManager fm, Context context, List<StickersSet> stickersSetList) {
            super(fm);
            mContext = context;
            mStickersSetsList = stickersSetList;
        }

        @Override
        public Fragment getItem(int position) {
            return StickersFragment.newInstance(position);
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
}
