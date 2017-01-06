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

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.presentation.common.BaseToolFragment;
import net.iquesoft.iquephoto.models.StickersSet;
import net.iquesoft.iquephoto.presentation.presenters.fragment.StickersPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.StickersView;
import net.iquesoft.iquephoto.util.ActivityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.STICKERS;

public class StickersToolFragment extends BaseToolFragment implements StickersView {
    @InjectPresenter
    StickersPresenter presenter;

    @BindView(R.id.stickerTabLayout)
    TabLayout tabLayout;

    @BindView(R.id.stickersViewPager)
    ViewPager viewPager;

    private List<StickersSet> mStickersSetsList = StickersSet.getStickersSetsList();

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView = (ImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stickers, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        StickersPagerAdapter pagerAdapter =
                new StickersPagerAdapter(getChildFragmentManager(), getContext(), mStickersSetsList);

        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            StickersSet stickersSet = mStickersSetsList.get(i);
            tab.setIcon(stickersSet.getIcon());
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImageEditorView.setCommand(STICKERS);
        ActivityUtil.updateToolbarTitle(R.string.stickers, getActivity());
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
