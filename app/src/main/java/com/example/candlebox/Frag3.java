package com.example.candlebox;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class Frag3 extends Fragment {
    public static final String TAG = "Frag3";
    public static String time;
    public static TextView tvTesterFrag3;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag3, container, false);
        TextView tvGreeting = (TextView) view.findViewById(R.id.tvGreeting3);
        tvTesterFrag3 = view.findViewById(R.id.tvTesterFrag3);

        //set personalized greeting based on current time
        returnTime();
        tvGreeting.setText(time + String.valueOf(ParseUser.getCurrentUser().getUsername()));

        //set tree statistic
        tvTesterFrag3.setText(String.valueOf(MainActivity.totalTrees));
        Log.i(TAG, String.valueOf(MainActivity.totalTrees));
        return view;
    }

    public static Frag3 newInstance() {
        return new Frag3();
    }

    //returns the beginning of a greeting based on the current time
    public static void returnTime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay >= 0 && timeOfDay < 12){
            time = "Good morning, ";
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            time = "Good afternoon, ";
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            time = "Good evening, ";
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            time = "Good night, ";
        }
    }
}
