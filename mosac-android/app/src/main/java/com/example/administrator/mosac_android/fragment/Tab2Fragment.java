package com.example.administrator.mosac_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.adapter.pagerAdapter;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Tab2Fragment extends Fragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    View view;
    //UI Objects
    private RadioGroup topmenu;
    private RadioButton all;
    private RadioButton study;
    private RadioButton sports;
    private RadioButton outdoor;
    private RadioButton game;
    private ViewPager teamlistviewpager;
    private pagerAdapter mAdapter;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    public static final int PAGE_FIVE = 4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab2,container,false);
        mAdapter = new pagerAdapter(getChildFragmentManager());
        mAdapter.bindUser(getArguments().getInt("user_id"));
        bindViews();
        all.setChecked(true);
        return view;
    }
    private void bindViews() {
        topmenu = (RadioGroup) view.findViewById(R.id.topmenu);
        all = (RadioButton) view.findViewById(R.id.all);
        study = (RadioButton) view.findViewById(R.id.study);
        sports = (RadioButton) view.findViewById(R.id.sports);
        outdoor = (RadioButton) view.findViewById(R.id.outdoor);
        game = (RadioButton) view.findViewById(R.id.game);
        topmenu.setOnCheckedChangeListener(this);
        teamlistviewpager = (ViewPager) view.findViewById(R.id.teamlistViewPager);
        teamlistviewpager.setAdapter(mAdapter);
        teamlistviewpager.setCurrentItem(0);
        teamlistviewpager.addOnPageChangeListener(this);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.all:
                teamlistviewpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.study:
                teamlistviewpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.sports:
                teamlistviewpager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.outdoor:
                teamlistviewpager.setCurrentItem(PAGE_FOUR);
                break;
            case R.id.game:
                teamlistviewpager.setCurrentItem(PAGE_FIVE);
                break;
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (teamlistviewpager.getCurrentItem()) {
                case PAGE_ONE:
                    all.setChecked(true);
                    break;
                case PAGE_TWO:
                    study.setChecked(true);
                    break;
                case PAGE_THREE:
                    sports.setChecked(true);
                    break;
                case PAGE_FOUR:
                    outdoor.setChecked(true);
                    break;
                case PAGE_FIVE:
                    game.setChecked(true);
                    break;
            }
        }
    }
}
