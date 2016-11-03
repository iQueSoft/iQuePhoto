package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.StickersPagerAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.StickersSet;
import net.iquesoft.iquephoto.presenter.StickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IStickersFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StickersFragment extends BaseFragment implements IStickersFragmentView {

    private List<StickersSet> mStickersSetsList = StickersSet.getStickersSetsList();

    private Unbinder mUnbinder;

    private StickersPagerAdapter mPagerAdapter;

    @Inject
    StickersFragmentPresenterImpl presenter;

    @BindView(R.id.stickerTabLayout)
    TabLayout tabLayout;

    @BindView(R.id.stickersLayout)
    LinearLayout stickersLayout;

    @BindView(R.id.stickersViewPager)
    ViewPager viewPager;

    public static StickersFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new StickersFragment();
    }

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
        View v = inflater.inflate(R.layout.fragment_stickers, container, false);

        mUnbinder = ButterKnife.bind(this, v);

        mPagerAdapter = new StickersPagerAdapter(getChildFragmentManager(), getContext(), mStickersSetsList);
        viewPager.setAdapter(mPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            StickersSet stickersSet = mStickersSetsList.get(i);
            tab.setIcon(stickersSet.getIcon());
        }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
