package net.iquesoft.iquephoto.ui.fragments;

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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.mvp.models.StickersSet;
import net.iquesoft.iquephoto.mvp.presenters.fragment.StickersPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.StickersView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.STICKERS;

public class StickersToolFragment extends MvpAppCompatFragment implements StickersView {
    @InjectPresenter
    StickersPresenter presenter;

    @BindView(R.id.stickerTabLayout)
    TabLayout tabLayout;

    @BindView(R.id.stickersViewPager)
    ViewPager viewPager;

    private List<StickersSet> mStickersSetsList = StickersSet.getStickersSetsList();

    private Unbinder mUnbinder;

    private StickersPagerAdapter mPagerAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
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

    @OnClick(R.id.stickersApplyImageButton)
    void onClickApply() {
        ((ImageEditorView) getActivity().findViewById(R.id.editorImageView)).apply(STICKERS);
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
