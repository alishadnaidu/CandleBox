package com.example.candlebox.CandleStuff.EmissionStats;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.candlebox.R;
import com.parse.ParseUser;

public class CarbonEmissionFragment extends Fragment {
    public static final String TAG = "Frag2";
    public static TextView tvTesterFrag2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2, container, false);

        //must use view.findViewById bc this is a fragment
        TextView tvGreeting2 = (TextView) view.findViewById(R.id.tvGreeting2);
        tvTesterFrag2 = (TextView) view.findViewById(R.id.tvTesterFrag2);

        // set personalized greeting based on current time
        TreesFragment.returnTime();
        tvGreeting2.setText(TreesFragment.time + ParseUser.getCurrentUser().getUsername());

        // set the amount of CO2 statistic
        tvTesterFrag2.setText(String.valueOf(MainActivity.amountOfCO2));
        Log.i(TAG, String.valueOf(MainActivity.amountOfCO2));

        return view;
    }

    //creates new instance of Frag2
    public static CarbonEmissionFragment newInstance() {
        return new CarbonEmissionFragment();
    }
}
