package com.example.candlebox;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.ParseUser;

public class Frag2 extends Fragment {
    public static final String TAG = "Frag2";
    public static TextView tvTesterFrag2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2, container, false);
        TextView tvGreeting2 = (TextView) view.findViewById(R.id.tvGreeting2);
        tvTesterFrag2 = (TextView) view.findViewById(R.id.tvTesterFrag2);

        // set personalized greeting based on current time
        Frag3.returnTime();
        tvGreeting2.setText(Frag3.time + ParseUser.getCurrentUser().getUsername());

        // set the amount of CO2 statistic
        tvTesterFrag2.setText(String.valueOf(MainActivity.totalHours));

        //Log.i("Frag2", String.valueOf(MainActivity.amountOfCO2));
        Log.i(TAG, String.valueOf(3732));
        return view;
    }

    public static Frag2 newInstance() {
        return new Frag2();
    }
}
