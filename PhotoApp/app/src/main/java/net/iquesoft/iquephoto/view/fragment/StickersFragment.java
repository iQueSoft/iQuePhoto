package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.StickersPagerAdapter;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IMainActivityComponent;
import net.iquesoft.iquephoto.presenter.StickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IStickersFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StickersFragment extends BaseFragment implements IStickersFragmentView {

    private boolean isHide = false;

    private Unbinder unbinder;
    private StickersPagerAdapter pagerAdapter;

    @Inject
    StickersFragmentPresenterImpl presenter;

    @BindView(R.id.hideStickersButton)
    ImageView hideStickersButton;

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
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stickers, container, false);
        v.setAlpha(0.85f);

        unbinder = ButterKnife.bind(this, v);

        pagerAdapter = new StickersPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.hideStickersButton)
    public void onClickHideStickers() {
        if (!isHide) {
            hideStickersButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            stickersLayout.setVisibility(View.GONE);
            isHide = true;
        } else {
            hideStickersButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            stickersLayout.setVisibility(View.VISIBLE);
            isHide = false;
        }
    }
}
