package com.example.administrator.mosac_android.adpater;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.mosac_android.fragment.AllFragment;
import com.example.administrator.mosac_android.fragment.GameFragment;
import com.example.administrator.mosac_android.fragment.OutdoorFragment;
import com.example.administrator.mosac_android.fragment.SportsFragment;
import com.example.administrator.mosac_android.fragment.StudyFragment;
import com.example.administrator.mosac_android.fragment.Tab2Fragment;
import com.example.administrator.mosac_android.model.User;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class pagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 5;
    private AllFragment allFragment = null;
    private StudyFragment studyFragment = null;
    private SportsFragment sportsFragment = null;
    private OutdoorFragment outdoorFragment = null;
    private GameFragment gameFragment = null;
    public pagerAdapter(FragmentManager fm) {
        super(fm);
        allFragment = new AllFragment();
        studyFragment = new StudyFragment();
        sportsFragment = new SportsFragment();
        outdoorFragment = new OutdoorFragment();
        gameFragment = new GameFragment();
    }
    public void bindUser(User user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        allFragment.setArguments(bundle);
        studyFragment.setArguments(bundle);
        sportsFragment.setArguments(bundle);
        outdoorFragment.setArguments(bundle);
        gameFragment.setArguments(bundle);
    }
    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case Tab2Fragment.PAGE_ONE:
                fragment = allFragment;
                break;
            case Tab2Fragment.PAGE_TWO:
                fragment = studyFragment;
                break;
            case Tab2Fragment.PAGE_THREE:
                fragment = sportsFragment;
                break;
            case Tab2Fragment.PAGE_FOUR:
                fragment = outdoorFragment;
                break;
            case Tab2Fragment.PAGE_FIVE:
                fragment = gameFragment;
                break;
        }
        return fragment;
    }
}
