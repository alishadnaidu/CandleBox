package com.example.candlebox.CandleStuff.EmissionStats;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.candlebox.R;
import com.parse.ParseUser;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.jetbrains.annotations.NotNull;

public class HoursFragment extends Fragment {

    public static final String TAG = "Frag1";
    public static TextView tvTesterFrag1;
    public static ValueLineChart mCubicValueLineChart;
    public static ValueLineSeries series;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1, container, false);

        //must use view.findViewById bc this is a fragment
        TextView tvGreeting1 = view.findViewById(R.id.tvGreeting1);
        tvTesterFrag1 = view.findViewById(R.id.tvTesterFrag1);

        // set personalized greeting based on the current time
        TreesFragment.returnTime();
        tvGreeting1.setText(TreesFragment.time + ParseUser.getCurrentUser().getUsername());

        // set the hours based on calculations from MainActivity
        tvTesterFrag1.setText(String.valueOf(MainActivity.totalHours));
        Log.i(TAG, String.valueOf(MainActivity.totalHours));

        mCubicValueLineChart = view.findViewById(R.id.cubiclinechart);
        series = new ValueLineSeries();
        series.setColor(0xFF92CEFF);
        mCubicValueLineChart.setShowIndicator(true);

        return view;
    }

    //creates new instance of Frag1(), used in adapter
    public static HoursFragment newInstance() {
        return new HoursFragment();
    }
}
