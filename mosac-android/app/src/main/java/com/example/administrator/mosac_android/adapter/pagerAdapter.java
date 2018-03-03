package com.example.administrator.mosac_android.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.mosac_android.fragment.Tab2Fragment;
import com.example.administrator.mosac_android.fragment.TeamBaseFragment;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class pagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 5;
    private TeamBaseFragment allFragment = null;
    private TeamBaseFragment studyFragment = null;
    private TeamBaseFragment sportsFragment = null;
    private TeamBaseFragment outdoorFragment = null;
    private TeamBaseFragment gameFragment = null;
    private int user_id;
    public pagerAdapter(FragmentManager fm) {
        super(fm);
        allFragment = new TeamBaseFragment();
        studyFragment = new TeamBaseFragment();
        sportsFragment = new TeamBaseFragment();
        outdoorFragment = new TeamBaseFragment();
        gameFragment = new TeamBaseFragment();
    }
    private void setArguments() {
        Bundle bundle1 = new Bundle();
        bundle1.putString("type", "");
        bundle1.putInt("user_id", user_id);
        allFragment.setArguments(bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putString("type", "学习");
        bundle2.putInt("user_id", user_id);
        studyFragment.setArguments(bundle2);
        Bundle bundle3 = new Bundle();
        bundle3.putString("type", "运动");
        bundle3.putInt("user_id", user_id);
        sportsFragment.setArguments(bundle3);
        Bundle bundle4 = new Bundle();
        bundle4.putString("type", "户外");
        bundle4.putInt("user_id", user_id);
        outdoorFragment.setArguments(bundle4);
        Bundle bundle5 = new Bundle();
        bundle5.putString("type", "游戏");
        bundle5.putInt("user_id", user_id);
        gameFragment.setArguments(bundle5);
    }
    public void bindUser(int user_id) {
        this.user_id = user_id;
        setArguments();
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
