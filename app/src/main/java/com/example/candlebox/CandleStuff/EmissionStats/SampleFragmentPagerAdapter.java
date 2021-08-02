package com.example.candlebox.CandleStuff.EmissionStats;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

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

    //opens correct fragment based on position
    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            HoursFragment myFragment = HoursFragment.newInstance();
            return myFragment;
        }
        if (position == 1) {
            CarbonEmissionFragment myFragment2 = CarbonEmissionFragment.newInstance();
            return myFragment2;
        }
        TreesFragment myFragment3 = TreesFragment.newInstance();
        return myFragment3;
    }

    // Generate title based on item position
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
