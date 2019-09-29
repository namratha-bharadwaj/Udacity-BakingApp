package com.example.android.bakingapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.android.bakingapp.fragments.StepDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class StepActivityPagerAdapter extends FragmentStatePagerAdapter {

    private List<StepDetailFragment> mFragments;

    public StepActivityPagerAdapter(FragmentManager fm, List<StepDetailFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size():0 ;
    }
}
