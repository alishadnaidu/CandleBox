package com.example.candlebox.ui;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.candlebox.Frag1;
import com.example.candlebox.Frag2;
import com.example.candlebox.Frag3;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Hours", "Grams CO2", "Trees" };
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        //return PageFragment.newInstance(position + 1);
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Frag1();
                break;
            case 1:
                fragment = new Frag2();
                break;
            case 2:
                fragment = new Frag3();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
